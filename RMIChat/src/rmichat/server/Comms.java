package rmichat.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import rmichat.core.MessageFormatter;
import rmichat.core.Models.Message;
import rmichat.core.Models.PrivateMessage;
import rmichat.core.Models.User;
import rmichat.core.Repositories.InMemoryUsersRepository;
import rmichat.core.Repositories.IUsersRepository;

/**
 *
 * @author Krzysztof
 */
public class Comms extends UnicastRemoteObject implements IComms {

    // <editor-fold desc="Private fields">
    
    private final IUsersRepository _allUsersRepo;
    private final ArrayList<PrivateMessage> _unreadPrivateMessages;
    private final MessageFormatter _messageFormatter = new MessageFormatter();
    
    // </editor-fold>
    
    public Comms() throws RemoteException {
        _allUsersRepo = new InMemoryUsersRepository(new ArrayList<>());
        _unreadPrivateMessages = new ArrayList<>();
    }
    
    // <editor-fold desc="Public methods">
    
    @Override
    public synchronized void registerUser(User user) throws RemoteException {
        _allUsersRepo.add(user);
        user.getRemoteInterface().receiveMessage("Welcome to global chat");
        System.out.println(_messageFormatter.userJoinedGlobalChat(user.getName()));
        
        List<PrivateMessage> registeredUserMessages = _unreadPrivateMessages.stream()
            .filter(message -> message.getRecipientName().equals(user.getName()))
            .collect(Collectors.toList());
        
        registeredUserMessages.sort((msg1, msg2) -> msg1.getTimestamp().compareTo(msg2.getTimestamp()));
        registeredUserMessages.forEach(message -> {
            try {
                sendPrivateMessage(user, message.getContent() + " (private with: " + message.getAuthor().getName() + ")");
            } catch (RemoteException ex) {
                Logger.getLogger(Comms.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    @Override
    public synchronized void receiveMessage(Message message) throws RemoteException {
        broadcastMessage(message);
    }
    
    @Override
    public synchronized void receivePrivateMessage(PrivateMessage message) throws RemoteException {
        User recipient = _allUsersRepo.getByUserName(message.getRecipientName());
        
        if (recipient == null) {
            sendPrivateMessage(message.getAuthor(), _messageFormatter.getWithPrivateMarkForAuthor(message));
            _unreadPrivateMessages.add(message);
            return;
        }
        
        sendPrivateMessage(message.getAuthor(), _messageFormatter.getWithPrivateMarkForAuthor(message));
        sendPrivateMessage(recipient, _messageFormatter.getWithPrivateMarkForRecipient(message));
    }

    @Override
    public synchronized void unregisterUser(User userLeaving) throws RemoteException {
        _allUsersRepo.remove(userLeaving);
        System.out.println(_messageFormatter.userLeftGlobalChat(userLeaving.getName()));
    }
    
    @Override
    public void saveCurrentUsers() throws RemoteException, FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(new File("users.txt"))) {
            for (User user : _allUsersRepo.getAll()) {
                printWriter.println(user.getName());
            }
        }
    }
    
    // </editor-fold>
    
    //<editor-fold desc="Private methods">
    
    private synchronized void broadcastMessage(Message message) throws RemoteException {
        _allUsersRepo.getAll().forEach(user -> {
            try {
                user.getRemoteInterface().receiveMessage(message.getContent());
            } catch (RemoteException ex) {
                Logger.getLogger(Comms.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    } 
    
    private synchronized void sendPrivateMessage(User recipient, String message) throws RemoteException {
        recipient.getRemoteInterface().receiveMessage(message);
    }
    
    //</editor-fold>
}
