import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Get client's local time
            LocalDateTime clientTime = LocalDateTime.now();
            System.out.println("Client time: " + clientTime);

            // Send client's local time to server
            outputStream.writeObject(clientTime);
            outputStream.flush();

            // Receive time difference from server
            long timeDifference = inputStream.readLong();
            System.out.println("Received time difference from server: " + timeDifference);

            // Calculate and set synchronized time
            LocalDateTime synchronizedTime = clientTime.plusNanos(timeDifference);
            System.out.println("Synchronized time: " + synchronizedTime);

            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
