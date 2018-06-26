/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Krzysztof
 */
public interface IClientComms extends Remote {
    public void sendMessage(String message) throws RemoteException;
    public void receiveMessage(String message) throws RemoteException;
    public void leaveChat() throws RemoteException;
    public String getUserName() throws RemoteException;
    public void notifyChatDisbanded() throws RemoteException;
    public void startPrivateChat(String user) throws RemoteException;
    public void notifyPrivateChatRequest(int chatId, String userName) throws RemoteException;
}
