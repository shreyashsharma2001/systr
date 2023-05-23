import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for client connections...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            // Receive client's local time
            LocalDateTime clientTime = (LocalDateTime) inputStream.readObject();
            System.out.println("Received client time: " + clientTime);

            // Get server's local time
            LocalDateTime serverTime = LocalDateTime.now();
            System.out.println("Server time: " + serverTime);

            // Calculate time difference
            long timeDifference = serverTime.toLocalTime().toNanoOfDay() - clientTime.toLocalTime().toNanoOfDay();

            // Send time difference to client
            outputStream.writeLong(timeDifference);
            outputStream.flush();
            System.out.println("Sent time difference to client: " + timeDifference);

            inputStream.close();
            outputStream.close();
            clientSocket.close();
            System.out.println("Server closed.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

