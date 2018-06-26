package rmichat.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Krzysztof
 */
public class ClientComms extends UnicastRemoteObject implements IClientComms {    
    // <editor-fold desc="Public methods">

    public ClientComms() throws RemoteException {};
    
    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }
    
    // </editor-fold>
}
