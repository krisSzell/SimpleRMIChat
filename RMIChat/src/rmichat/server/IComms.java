package rmichat.server;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import rmichat.core.Models.Message;
import rmichat.core.Models.PrivateMessage;
import rmichat.core.Models.User;

/**
 *
 * @author Krzysztof
 */
public interface IComms extends Remote {
    void receiveMessage(Message message) throws RemoteException;
    void receivePrivateMessage(PrivateMessage message) throws RemoteException;
    void registerUser(User newUser) throws RemoteException;
    void unregisterUser(User userLeaving) throws RemoteException;
    public void saveCurrentUsers() throws RemoteException, FileNotFoundException;
}
