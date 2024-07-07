import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UprizeClient {

    private Socket socket = null;
    private BufferedReader inFromServer = null;
    private PrintWriter outToServer = null;

    private final String serverAddress = "localhost";
    private final int serverPort = 8080;

    public UprizeClient() {
    try {
            socket = new Socket(serverAddress, serverPort);
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToServer = new PrintWriter(socket.getOutputStream(), true);
            while(true){
                String response;
                System.out.println();
                response = inFromServer.readLine();
                //System.out.println(response);
            
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
    UprizeClient client = new UprizeClient();
   
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
            String command = getUserInput();
            System.out.println();
    
            // Send the login command with username and password
            outToServer.println(command);
    
            // Receive the login response from the server
            System.out.println();
            String response = inFromServer.readLine();
            System.out.println(response);
            System.out.println();
           
            if (response.startsWith("Welcome")) {
                isLoginRequestValid = true; // Exit the loop since login was successful

                handleLoggedInActions();
                                
            } else if (response.equals("Invalid login request format. Please use the following format: login username password.")) {
                // Handle invalid login request format
               // System.out.println("Invalid login request format. Please use the following format: login username password.");
            } else if (response.equals("If you forgot your password, please enter your member number and telephone number.")) {
                // Handle password recovery
                handlePasswordRecovery();
                
            }else {
                System.out.println("Login failed. Contact web system administrator.");
            }
        }

    }

    
    private void handlePasswordRecovery() throws IOException {
        
        // Prompt for member number and phone number
        System.out.println();
        String recoveryInfo = getUserInput();

        // Send the recovery information to the server
        outToServer.println(recoveryInfo);

        // Receive and print the response from the server
        System.out.println();
        String recoveryResponse = inFromServer.readLine();
        System.out.println(recoveryResponse);
        System.out.println();

        // Check if the password recovery is successful and prompt for the new password
        if (recoveryResponse.startsWith("Please use this temporary password to log in ")) {

             // Prompt for username and password
            System.out.println();
            String command = getUserInput();
            System.out.println();
    
            // Send the login command with username and password
            outToServer.println(command);
    
            // Receive the login response from the server
            System.out.println();
            String response = inFromServer.readLine();
            System.out.println(response);
            System.out.println();
           
            if (response.startsWith("Welcome")) {
                handleLoggedInActions();
                
            }
        }else if(recoveryResponse.startsWith("No match found for the provided member number")){
            System.out.println("See You Again......");
            
        }
      
    }
    

    private void handleLoggedInActions() throws IOException { 
        displayMemberMessages();

   //Continuously handle user commands until the user logs out   
    while (true) {
        System.out.println();
        
            displayMenu();
       
        // Prompt for the user's action
        System.out.println();
        System.out.print("Enter your preferred option: ");
        String userCommand = getUserInput();
        System.out.println();
        System.out.println();
  
        // Send the user command to the server
        outToServer.println(userCommand);

        // Receive and print the response from the server
          String serverResponse = inFromServer.readLine();             
            System.out.println(serverResponse);
            System.out.println();

        if(serverResponse.endsWith("pending")){
            System.out.println("Under Processing.....");
        }else if(serverResponse.startsWith("<<<< Loan application details >>>> ")){
            String finalResponse;
             while((finalResponse = inFromServer.readLine()) != null && !finalResponse.equals(" ")){
                System.out.println(finalResponse);
            }
            System.out.println();
    
            System.out.print("Do you want to accept the loan? (Type 'accept' or 'reject')");
            System.out.println();
            System.out.println();
            String decision = getUserInput();
            System.out.println();

            outToServer.println(decision);
            
            String finalResponse2;
            while((finalResponse2 = inFromServer.readLine()) != null && !finalResponse2.equals(" ")){
                System.out.println(finalResponse2);
            }
            System.out.println();

        }else if(serverResponse.startsWith("Hi")){

            while((serverResponse = inFromServer.readLine()) != null && !serverResponse.equals(" ")){
                System.out.println(serverResponse);
            }
            System.out.println();

        }

        if (serverResponse.startsWith("Dear")) {
             while((serverResponse = inFromServer.readLine()) != null && !serverResponse.equals(" ")){
                System.out.println(serverResponse);
            }
            System.out.println();
        }

        if (serverResponse.startsWith("            Loan Repayment Successful!")) {
             while((serverResponse = inFromServer.readLine()) != null && !serverResponse.equals(" ")){
                System.out.println(serverResponse);
            }
            System.out.println();
        }

        // Check if the user wants to log out
        if (serverResponse.startsWith("Dear") && serverResponse.contains("You have successfully logged out of your Uprise account")) {
             while((serverResponse = inFromServer.readLine()) != null && !serverResponse.equals(" ")){
                System.out.println(serverResponse);
            }
            System.out.println();
            break;
        }
        
    }

    // Close connections
    inFromServer.close();
    outToServer.close();
    socket.close();
}


    

    private static String getUserInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private  void displayMemberMessages() throws IOException {
        outToServer.println("GetmemberMessages");

        String response;
            while ((response = inFromServer.readLine()) != null && !response.equals(" ")) {
                System.out.println(response);
            }
    }

    private static void displayMenu() {
        try {
            System.out.println();
            System.out.println("==================================================================================================");
            System.out.println("                            * Explore UPRISE SACCO Services *                                     ");
            System.out.println("==================================================================================================");
            System.out.println();
            System.out.println("1. Deposit: 'deposit amount date_deposited receipt_number' (e.g deposit  10000  2020-02-01  R-001)");
            System.out.println();
            System.out.println("2. Check Statement: 'CheckStatement dateFrom DateTo' (e.g CheckStatement 2020-02-01  2020-06-01)");
            System.out.println();
            System.out.println("3. Request Loan: 'requestLoan amount paymentPeriod_in_months' (e.g requestLoan  10000  3)");
            System.out.println();
            System.out.println("4. Loan Status: 'LoanRequestStatus loan_application_number' (e.g LoanRequestStatus  APP-e1f2f76v-t)");
            System.out.println();
            System.out.println("5. To pay loan: (Type 'payLoan')");
            System.out.println();
            System.out.println("6. Logout of the system: 'logout'");
            System.out.println();
            System.out.println("Our team is here to assist you with any requests or queries you may have.");
            System.out.println("================================================================================================\n");
            
        } catch (Exception e) {
            System.out.println(e);
            
        }
    }
    

}
