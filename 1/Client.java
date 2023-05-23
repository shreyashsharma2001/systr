import java.rmi.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            ServerInterface server = (ServerInterface) Naming.lookup("rmi://localhost/Server");

            int clientId = server.getNumClients();
            server.addClient();
            System.out.println("Client " + clientId + " connected to server.");

            while (true) {
                System.out.println("Enter a message to send to the server: ");
                String message = scanner.nextLine();
                String response = server.processMessage(message);
                System.out.println("Server responded with: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
