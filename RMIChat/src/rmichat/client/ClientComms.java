/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import rmichat.server.IComms;

/**
 *
 * @author Krzysztof
 */
public class ClientComms extends UnicastRemoteObject implements IClientComms {

    private int _currentChatId;
    private final String _userName;
    private final IComms _server;

    public ClientComms(String userName, IComms server) throws RemoteException {
        _userName = userName;
        _server = server;
        _currentChatId = 0;
    }
    
    @Override
    public void sendMessage(String message) throws RemoteException {
        if (_currentChatId == -1) {
            return;
        }
        _server.receiveMessage(_currentChatId, message, _userName);
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public String getUserName() {
        return _userName;
    }

    @Override
    public void leaveChat() throws RemoteException {
        _server.unregisterChatClient(_currentChatId, this);
        _currentChatId = -1;
    }

    @Override
    public void notifyChatDisbanded() throws RemoteException {
        System.out.println("Chat has been disbanded, please type 'exit' to leave");
    }

    @Override
    public void startPrivateChat(String user) throws RemoteException {
        _server.openPrivateChat(this, user);
    }

    @Override
    public void notifyPrivateChatRequest(int chatId, String userName) throws RemoteException {
        System.out.println("User " + userName + " wants to start a private chat. Proceed? (y/n)");
        Scanner userInput = new Scanner(System.in);
        String line = userInput.nextLine();
        if (line.equals("y")) {
            _currentChatId = chatId;
        }
        if (line.equals("n")) {
            _server.unregisterChatClient(chatId, this);
        }
    }
}
