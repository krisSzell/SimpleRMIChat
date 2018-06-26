package rmichat.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Krzysztof
 */
public interface IClientComms extends Remote {
    public void receiveMessage(String message) throws RemoteException;
}
