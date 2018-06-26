/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import rmichat.client.IClientComms;

/**
 *
 * @author Krzysztof
 */
public interface IComms extends Remote {
    void receiveMessage(int chatGroupId, String message, String senderName) throws RemoteException;
    void registerChatClient(IClientComms clientComms) throws RemoteException;
    int openPrivateChat(IClientComms user, String friendName) throws RemoteException;
    void unregisterChatClient(int chatId, IClientComms userLeaving) throws RemoteException;
    String pingSignal() throws RemoteException;
}
