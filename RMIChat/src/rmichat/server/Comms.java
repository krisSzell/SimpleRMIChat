/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmichat.client.IClientComms;
import rmichat.core.MessageFormatter;
import rmichat.core.TimeStampProvider;

/**
 *
 * @author Krzysztof
 */
public class Comms extends UnicastRemoteObject implements IComms {

// <editor-fold desc="Private fields">
    
    private static int NEXT_PRIVATE_CHAT_ID = 0;
    private final ArrayList<IClientComms> _globalChat;
    private final HashMap<Integer, ArrayList<IClientComms>> _privateChats;
    private final TimeStampProvider _timestampProvider = new TimeStampProvider();
    private final MessageFormatter _messageFormatter = new MessageFormatter(_timestampProvider);
    
// </editor-fold>
    
    public Comms() throws RemoteException {
        _globalChat = new ArrayList<>();
        _privateChats = new HashMap<>();
    }
    
    @Override
    public void registerChatClient(IClientComms clientComms) throws RemoteException {
        _globalChat.add(clientComms);
        System.out.println(_messageFormatter.userJoinedGlobalChat(clientComms.getUserName()));
    }

    @Override
    public String pingSignal() throws RemoteException {
        return _timestampProvider.getCurrent() + " Server has pinged you";
    }
    
    @Override
    public void receiveMessage(int chatGroupId, String message, String senderName) throws RemoteException {
        if (chatGroupId == 0) {
            broadcastMessage(_globalChat, message, senderName);
        } 
        else {
            broadcastMessage(_privateChats.get(chatGroupId), message, senderName);
        }   
    }
    
    @Override
    public int openPrivateChat(IClientComms user, String friendName) throws RemoteException {
        IClientComms foundFriend = null;
        for (IClientComms client : _globalChat) {
            String clientName = client.getUserName();
            if (clientName.equals(friendName)) {
                foundFriend = client;
            }
        }
        
        if (foundFriend == null) {
            return -1;
        }
        
        int id = ++NEXT_PRIVATE_CHAT_ID;
        
        ArrayList<IClientComms> privateChatMembers = new ArrayList<>();
        privateChatMembers.add(user);
        privateChatMembers.add(foundFriend);
        
        _privateChats.put(id, privateChatMembers);
        
        foundFriend.notifyPrivateChatRequest(id, user.getUserName());
        
        return id;
    }
    
    private void broadcastMessage(ArrayList<IClientComms> chatGroup, String message, String senderName) throws RemoteException {
        chatGroup.forEach(client -> {
            try {
                client.receiveMessage(_messageFormatter.includeTimeStampAndUserName(message, senderName));
            } catch (RemoteException ex) {
                Logger.getLogger(Comms.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }   

    @Override
    public void unregisterChatClient(int chatId, IClientComms userLeaving) throws RemoteException {
        if (chatId == 0) {
            _globalChat.remove(userLeaving);
            System.out.println(_messageFormatter.userLeftGlobalChat(userLeaving.getUserName()));
        }
        else {
            ArrayList<IClientComms> privateChatMembers = _privateChats.get(chatId);
            privateChatMembers.forEach(member -> {
                try {
                    member.notifyChatDisbanded();
                } catch (RemoteException ex) {
                    Logger.getLogger(Comms.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            _privateChats.remove(chatId);        
        }
    }
}
