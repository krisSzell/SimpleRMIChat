package rmichat.client;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmichat.core.Models.Message;
import rmichat.core.Models.PrivateMessage;
import rmichat.core.Models.User;
import rmichat.server.IComms;

/**
 *
 * @author Krzysztof
 */
public class ChattingService {
    
    // <editor-fold desc="Private fields">
    
    private final User _user;
    private final IComms _server;
    
    // </editor-fold>
    
    public ChattingService(User client, IComms server) {
        _user = client;
        _server = server;
    }
    
    // <editor-fold desc="Public methods">
    
    public void startGlobalChat() throws RemoteException {
        _server.registerUser(_user);
        Scanner userInput = new Scanner(System.in);
        String line;
        while (!(line = userInput.nextLine()).equals("exit")) {
            try {
                String[] parts = line.split(" ");
                if (parts[0].equals("-p")) {
                    _server.receivePrivateMessage(new PrivateMessage(_user, parts[1], String.join(" ", Arrays.copyOfRange(parts, 2, parts.length))));
                } else {
                    _server.receiveMessage(new Message(_user, line));
                }                
            } catch (RemoteException ex) {
                Logger.getLogger(ChattingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        _server.unregisterUser(_user);
        System.out.println("Left global chat.");
    }
    
    // </editor-fold>
}
