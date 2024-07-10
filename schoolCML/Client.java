import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket = null;
    private BufferedReader inFromServer = null;
    private PrintWriter outToServer = null;

    private final String serverAddress = "localhost";
    private final int serverPort = 8080;

    public Client() {
    try {
            socket = new Socket(serverAddress, serverPort);
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToServer = new PrintWriter(socket.getOutputStream(), true);
            while(true){
                String response;
                System.out.println();
                response = inFromServer.readLine();
                System.out.println(response);
            
            // Check if the response indicates the need to log in
            if (response.equals("Please log in using the command: 'login  username  password' (e.g login  allan21  password1111)")) {
                login();
            }

            }
            
            
        } catch (Exception e) {
            System.out.println("Failed to connect to the server.");
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
    Client client = new Client();
   
    }

    
    private void login() throws IOException {
        boolean isLoginRequestValid = false;

        while (!isLoginRequestValid) {
            System.out.println("***********************************************************************************************");
            System.out.println("****                                    UPRISE SACCO                                       ****");
            System.out.println("****                            Your official financial partner                            ****");
            System.out.println("***********************************************************************************************");
            System.out.println("Please log in using the command: 'login  username  password' (e.g login  allan21  password1111)");
            
              // Prompt for username and password
            System.out.println();
            System.out.println();
    
            // Receive the login response from the server
            System.out.println();
            String response = inFromServer.readLine();
            System.out.println(response);
            System.out.println();
           
            if (response.startsWith("Welcome")) {
                isLoginRequestValid = true; 
                                
            } else if (response.equals("Invalid login request format. Please use the following format: login username password.")) {
                // Handle invalid login request format
               // System.out.println("Invalid login request format. Please use the following format: login username password.");
            } else if (response.equals("If you forgot your password, please enter your member number and telephone number.")) {
                // Handle password recovery
                
            }else {
                System.out.println("Login failed. Contact web system administrator.");
            }
        }

    }    

}
