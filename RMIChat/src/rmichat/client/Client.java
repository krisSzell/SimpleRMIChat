package rmichat.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import rmichat.core.Models.User;
import rmichat.core.UserFactory;
import rmichat.server.IComms;

/**
 *
 * @author Krzysztof
 */
public class Client {
    private final static String HOST_NAME = "localhost";
    private final static String SERVICE_NAME = "ChatService";
    private final static UserFactory _userFactory = new UserFactory();
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, InterruptedException {
        try {
            IComms server = (IComms) Naming.lookup("rmi://" + HOST_NAME + "/" + SERVICE_NAME);
            System.out.println("To enter the global chat please give me your name:");
            Scanner userNameInput = new Scanner(System.in);
            String userName = userNameInput.nextLine();
            User client = _userFactory.getNew(userName, new ClientComms());
            ChattingService chattingService = new ChattingService(client, server);
            System.out.println("Client is up and running");

            chattingService.startGlobalChat();
        }
        catch (MalformedURLException | NotBoundException | RemoteException exception) {
            System.out.println("Client has failed to start... " + exception);
        }
    }
}
