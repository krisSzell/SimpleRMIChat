/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author Krzysztof
 */
public class Server{
    private final static String _hostName = "localhost";
    private final static String _serviceName = "ChatService";
    
    protected Server() throws RemoteException {}
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        startRMIRegistry();
        
        try {
            IComms server = new Comms();
            Naming.rebind("rmi://" + _hostName + "/" + _serviceName, server);
            System.out.println("Chat server is up and running...");
        }
        catch (Exception exception) {
            System.out.println("Chat server failed... " + exception);
        }
    }
    
    private static void startRMIRegistry() {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI Server ready");
        }
        catch (RemoteException exception) {
            exception.printStackTrace();
        }
    }
}
