/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmichat.server.IComms;

/**
 *
 * @author Krzysztof
 */
public class Client {
    private final static String _hostName = "localhost";
    private final static String _serviceName = "ChatService";
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, InterruptedException {
        try {
            IComms server = (IComms) Naming.lookup("rmi://" + _hostName + "/" + _serviceName);
            System.out.println("To enter the global chat please give me your name:");
            Scanner userNameInput = new Scanner(System.in);
            String userName = userNameInput.nextLine();
            IClientComms client = new ClientComms(userName, server);
            ChattingService chattingService = new ChattingService(client, server);
            System.out.println("Client is up and running");
            System.out.println(server.pingSignal());
            
            Thread globalChatThread = new Thread(() -> {
                try {
                    chattingService.startGlobalChat();
                } catch (RemoteException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            globalChatThread.start();
            globalChatThread.join();
            
            new Thread(() -> {
                printMenuStub();
                Scanner userInput = new Scanner(System.in);
                String line;
                while (!(line = userInput.nextLine()).equals(3)) {
                    switch (line) {
                        case "1": {
                        try {
                            chattingService.startGlobalChat();
                        } catch (RemoteException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            break;
                        }
                        case "2": {
                        try {
                            chattingService.startPrivateChat();
                        } catch (RemoteException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            break;
                        }
                    }
                }
            }).start();
        }
        catch (MalformedURLException | NotBoundException | RemoteException exception) {
            System.out.println("Client has failed to start... " + exception);
        }
    }
    
    private static void printMenuStub() {
        System.out.println("Menu:");
                System.out.println("1. Global chat");
                System.out.println("2. Private chats");
                System.out.println("3. Exit");
    }
}
