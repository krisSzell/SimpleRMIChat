package rmichat.server;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/**
 *
 * @author Krzysztof
 */
public class Server{
    private final static String HOST_NAME = "localhost";
    private final static String SERVICE_NAME = "ChatService";
    
    protected Server() throws RemoteException {}
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, FileNotFoundException {
        startRMIRegistry();
        
        try {
            IComms server = new Comms();
            Naming.rebind("rmi://" + HOST_NAME + "/" + SERVICE_NAME, server);
            System.out.println("Chat server is up and running...");
            Scanner userInput = new Scanner(System.in);
            String command;
            while (!(command = userInput.nextLine()).equals("terminate")) {
                if (command.toLowerCase().equals("saveuserstofile")) {
                    server.saveCurrentUsers();
                }
            }
        }
        catch (MalformedURLException | RemoteException exception) {
            System.out.println("Chat server failed... " + exception);
        }
    }
    
    private static void startRMIRegistry() {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI Server ready");
        }
        catch (RemoteException exception) {
            System.out.println("Starting RMI registry failed... " + exception);
        }
    }
}
