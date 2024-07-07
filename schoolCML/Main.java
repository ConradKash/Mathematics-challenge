import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        // Create a database instance
        Database database = new Database();

        try {
            // Create a new ServerSocket on the specified port
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started successfully. Listening on port " + PORT);

            while (true) {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();
                System.out.println("New user connected: " + clientSocket.getInetAddress());

               
                 ClientHandler clientHandler = new ClientHandler(clientSocket, database); //passing the database instance

                // Create a new thread to handle the client's requests
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Error while setting up the server: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error while closing the server socket: " + e.getMessage());
            }
        }
    }
    
}
