/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.client;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmichat.server.IComms;

/**
 *
 * @author Krzysztof
 */
public class ChattingService {
    private final IClientComms _client;
    private final IComms _server;
    
    public ChattingService(IClientComms client, IComms server) {
        _client = client;
        _server = server;
    }
    
    public void startGlobalChat() throws RemoteException {
        _server.registerChatClient(_client);
        System.out.println("Connected to global chat");
        Scanner userInput = new Scanner(System.in);
        String line;
        while (!(line = userInput.nextLine()).equals("exit")) {
            try {
                _client.sendMessage(line);
            } catch (RemoteException ex) {
                Logger.getLogger(ChattingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        _client.leaveChat();
        System.out.println("Left global chat.");
    }

    public void startPrivateChat() throws RemoteException {
        System.out.println("Enter name of a user you want to start a private chat with:");
        Scanner userToStartPrivateChat = new Scanner(System.in);
        String user = userToStartPrivateChat.nextLine();
        _client.startPrivateChat(user);
        System.out.println("Private chat with " + user + ":");
        
        Scanner userInput = new Scanner(System.in);
        String line;
        while (!(line = userInput.nextLine()).equals("exit")) {
            try {
                _client.sendMessage(line);
            } catch (RemoteException ex) {
                Logger.getLogger(ChattingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        _client.leaveChat();
        System.out.println("Left private chat.");
    }
}
