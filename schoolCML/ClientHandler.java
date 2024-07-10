import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ClientHandler implements Runnable {

    private static final int LOAN_REQUEST_THRESHOLD = 10;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private SaccoMember saccoMember;
    private Database database;

    public ClientHandler(Socket socket, Database database) {
        this.clientSocket = socket;
        this.database = database;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {

            out.println(
                    "Enter Numbers from 1 to 10");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("From Client: " + inputLine);
                String[] command = inputLine.split(" ");
                if (command.length != 3) {
                    database.availableSchools();
                    out.println("Invalid command. Usage: login username password");

                } else if (command[0].equalsIgnoreCase("login") && command.length == 3) {
                    handleLogin(command);
                    break;
                } else if (inputLine == "1"){
                    handleRegistration();
                    break;
                } else {
                    out.println("Invalid command. Please log in using the command 'login username password'");
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
    private void handleRegistration() {

    }

    private void handleLogin(String[] command) throws SQLException, IOException {
        System.out.println(command);
        String username = command[1];
        String password = command[2];
        // boolean isAuthenticated = Authentication.authenticate(username, password);
        boolean isAuthenticated = database.isValidCredentials(username, password);
        if (isAuthenticated) {
            // Retrieve the memberNumber based on the authenticated username
            String memberNumber = database.getMemberNumberByUsername(username);
            String surname = database.getSurnameByUsername(username);
            String firstName = database.getFirstNameByUsername(username);
            String gender = database.getGenderByUsername(username);

            if (memberNumber != null) {
                // Create and set the saccoMember object after successful authentication
                // Set the memberNumber for the current saccoMember
                saccoMember = new SaccoMember(username, memberNumber, surname, firstName, gender);
                out.println("Welcome back, " + username + "!                                              Date: "
                        + LocalDate.now());
                out.println(
                        "We're glad to have you here at Uprise sacco. Remember that your financial well-being is our priority.");
                out.println(
                        "Feel free to explore your account details, manage transactions, and access our range of services.");

                System.out.println("Login successful!!!!!!!!");
                String res = in.readLine();
                System.out.println(res);

                List<String> memberMessages = database.getMessagesForMember(memberNumber);
                if (!memberMessages.isEmpty()) {
                    out.println("\n----------------- You have new messages -------------------");
                    for (String message : memberMessages) {
                        out.println("- " + message);
                    }
                    out.println("------------------------------------------------------------\n");

                }

                boolean hasLoan = database.getLoanForAMember(saccoMember.getMemberNumber());

                if (hasLoan) {
                    // Check loan and contribution progress for warning
                    double loanProgress = calculateLoanProgressForSpecificMember(saccoMember.getMemberNumber());

                    if (loanProgress < 50) {
                        out.println("Warning: Your loan progress is less than 50%.");
                        out.println();
                    }
                }

                double contributionProgress = calculateContributionProgress(saccoMember.getMemberNumber());
                if (contributionProgress < 50) {
                    out.println("Warning: Your contribution progress is less than 50%.");
                }

                out.println(" ");

                List<String> memberMessage = database.getMessagesForMember(memberNumber);
                if (!memberMessages.isEmpty()) {
                    System.out.println("\n----------------- You have new messages -------------------");
                    for (String message : memberMessage) {
                        System.out.println("- " + message);
                    }
                    System.out.println("------------------------------------------------------------\n");

                }

                handleAuthenticatedMember();
                System.out.println("Done handling authenticated member");

            } else {
                out.println("Failed to retrieve member information. Please try again later.");
            }
        } else {
            // The user is not authenticated, so let's proceed with additional steps
            out.println("If you forgot your password, please enter your member number and telephone number.");

            // Read member number and telephone number from the user
            String inputLine = readInput();
            System.out.println(inputLine);

            String[] memberDetails = inputLine.split(" ");
            if (memberDetails.length != 2) {
                out.println("Invalid command. Usage: Member_Number  Telephone_Number");
            } else {
                String memberNumber = memberDetails[0];
                String phoneNumber = memberDetails[1];

                // Check if there's a match for member number and telephone number in the
                // database
                boolean isMatchFound = database.checkMemberNumberAndPhoneNumberInDatabase(memberNumber, phoneNumber);
                if (isMatchFound) {
                    // Generate a random temporary password
                    String temporaryPassword = generateRandomPassword();

                    // Update the randomPassword in the database for the member

                    database.updateMemberRandomPassword(memberNumber, temporaryPassword);

                    out.println("Please use this temporary password to log in: " + temporaryPassword);

                    // Wait for client to provide login input
                    String loginInput = in.readLine();
                    String[] loginCommand = loginInput.split(" ");

                    if (loginCommand.length == 3 && loginCommand[0].equalsIgnoreCase("login")) {
                        String loginUsername = loginCommand[1];
                        String loginPassword = loginCommand[2];
                        boolean isLoginValid = database.isValidCredential(loginUsername, loginPassword);

                        if (isLoginValid) {
                            // Retrieve the memberNumber based on the authenticated username
                            String memberNo = database.getMemberNumberByUsername(loginUsername);
                            String surname = database.getSurnameByUsername(loginUsername);
                            String firstName = database.getFirstNameByUsername(loginUsername);
                            String gender = database.getGenderByUsername(loginUsername);

                            if (memberNo != null) {
                                // Create and set the saccoMember object after successful authentication
                                // Set the memberNumber for the current saccoMember
                                saccoMember = new SaccoMember(loginUsername, memberNo, surname, firstName, gender);
                                out.println("Welcome back, " + loginUsername
                                        + "!                                          Date: " + LocalDate.now());
                                out.println(
                                        "We're glad to have you here at Uprise sacco. Remember that your financial well-being is our priority.");
                                out.println(
                                        "Feel free to explore your account details, manage transactions, and access our range of services.");

                                System.out.println("Login successful!!!!!!!!");
                                String res = in.readLine();
                                System.out.println(res);

                                List<String> memberMessage = database.getMessagesForMember(memberNo);
                                if (!memberMessage.isEmpty()) {
                                    out.println("\n----------------- You have new messages -------------------");
                                    for (String message : memberMessage) {
                                        out.println("- " + message);
                                    }
                                    out.println("------------------------------------------------------------\n");

                                }

                                boolean hasLoan = database.getLoanForAMember(saccoMember.getMemberNumber());

                                if (hasLoan) {
                                    // Check loan and contribution progress for warning
                                    double loanProgress = calculateLoanProgressForSpecificMember(
                                            saccoMember.getMemberNumber());

                                    if (loanProgress < 50) {
                                        out.println("Warning: Your loan progress is less than 50%.");
                                        out.println();
                                    }
                                }

                                double contributionProgress = calculateContributionProgress(
                                        saccoMember.getMemberNumber());
                                if (contributionProgress < 50) {
                                    out.println("Warning: Your contribution progress is less than 50%.");
                                }

                                out.println(" ");

                                handleAuthenticatedMember();
                                System.out.println("Done handling authenticated member");

                            } else {
                                out.println("Failed to retrieve member information. Please try again later.");
                            }

                        } else {
                            // Invalid login credentials
                            String referenceNumber = generateReferenceNumber();
                            out.println(
                                    "Invalid login credentials. Please return another day with this reference number: "
                                            + referenceNumber);
                        }
                    } else {
                        // Invalid login format
                        out.println(
                                "Invalid login format. Please use the following format: login username randomPassword");
                    }
                } else {
                    // No match found, generate a reference number for the user to follow up later
                    String referenceNumber = generateReferenceNumber();
                    // out.println("No match found for the provided member number and telephone
                    // number.");
                    out.println(
                            "No match found for the provided member number and telephone number. Please return another day with this reference number: "
                                    + referenceNumber);
                    System.out.println(
                            "No match found for the provided member number and telephone number. Please return another day with this reference number: "
                                    + referenceNumber);

                }
            }
        }

    }

    // Helper method to read input from the client
    private String readInput() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int passwordLength = 8;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private static String generateReferenceNumber() {
        // You can use UUID to generate a random reference number
        UUID uuid = UUID.randomUUID();
        String referenceNumber = uuid.toString().substring(0, 10).toUpperCase();
        return "Ref-" + referenceNumber;
    }

    private static String generateUniqueApplicationNumber() {
        // Generate a unique application number using UUID
        String uuid = UUID.randomUUID().toString();
        return "APP-" + uuid.substring(0, 10);
    }
