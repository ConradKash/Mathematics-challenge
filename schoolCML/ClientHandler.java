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
                    "Please log in using the command: 'login  username  password' (e.g login  allan21  password1111)");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("From Client: " + inputLine);
                String[] command = inputLine.split(" ");
                if (command.length != 3) {
                    out.println("Invalid command. Usage: login username password");

                } else if (command[0].equalsIgnoreCase("login") && command.length == 3) {
                    handleLogin(command);
                    break;
                } else {
                    out.println("Invalid command. Please log in using the command 'login username password'");
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

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

    private void handleAuthenticatedMember() throws SQLException {
        try {
            String inputLine;
            while (true) {

                inputLine = in.readLine();
                if (inputLine.isEmpty()) {
                    out.println(
                            "Hey there, " + saccoMember.getUsername() + ". It looks like you entered an empty choice.");
                    out.flush();
                } else {

                    String[] command = inputLine.split(" ");
                    switch (command[0]) {
                        case "deposit":
                            // Process deposit command
                            handleDeposit(command);
                            break;
                        case "CheckStatement":
                            // Process check statement command
                            handleCheckStatement(command);
                            break;
                        case "requestLoan":
                            // Process request loan command
                            handleRequestLoan(command);
                            break;
                        case "LoanRequestStatus":
                            // Process loan status command
                            handleLoanRequestStatus(command);
                            break;
                        case "payLoan":
                            // Process loan status command
                            performAutomaticDeductions(saccoMember.getMemberNumber());
                            break;
                        case "logout":
                            out.println("Dear " + saccoMember.getUsername() + ",");
                            out.println("You have successfully logged out of your Uprise account.");
                            out.println("Thank you for choosing Uprise as your financial partner. Have a great day!");
                            out.println(" ");
                            return;
                        default:
                            out.println("Invalid option. Please select a valid option.");
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeposit(String[] command) {
        if (command.length != 4) {
            out.println("Invalid command. Usage: deposit amount date_deposited receipt_number");
        } else {
            try {
                double amount = Double.parseDouble(command[1]);
                String dateDeposited = command[2];
                String receiptNumber = command[3];

                // Check if the receipt number exists in the database
                if (database.isValidReceiptNumber(receiptNumber, amount, dateDeposited,
                        saccoMember.getMemberNumber())) {
                    out.println("Dear " + saccoMember.getUsername() + ",");
                    out.println("We're pleased to inform you that your deposit of UGX" + amount
                            + " has been successfully recorded and credited to your account.");
                    out.println("Your commitment to saving with us is greatly appreciated.");
                    out.println(
                            "If you have any questions or need further assistance, please don't hesitate to reach out to our Customer Service Team at +256771895380 or uprizesacco21@gmail.com");
                    out.println("Thank you for being a valued member of Uprise.");
                    out.println(" ");
                } else {
                    // out.println("Invalid receipt number or receipt not yet uploaded. Please check
                    // later after '30 minutes'. Thank you.");
                    out.println("Dear " + saccoMember.getUsername() + ",");
                    out.println("We hope this finds you well.");
                    out.println("We wanted to inform you that we have not yet received the deposit upload for receipt "
                            + receiptNumber + " in your Uprise account.");
                    out.println(
                            "If you have already made the deposit, please allow 24 hours for the transaction to reflect in our records.");
                    out.println("Thank you.");
                    out.println(" ");
                }
            } catch (NumberFormatException e) {
                out.println("Invalid amount. Please enter a valid number.");
            }
        }
    }

    private void handleRequestLoan(String[] command) {
        if (command.length != 3) {
            out.println("Invalid choice. Usage: requestLoan amount paymentPeriod_in_months");
        } else {
            try {
                double requestedAmount = Double.parseDouble(command[1]);
                int paymentPeriod = Integer.parseInt(command[2]);

                // Check if the member already has a pending loan application
                if (saccoMember.hasPendingLoanApplications()) {
                    out.println("WARNING: You already have a pending loan application.");
                } else {
                    // Generate a unique application number for the loan
                    String applicationNumber = generateUniqueApplicationNumber();

                    // Get the current date as the application date
                    Date applicationDate = new Date();

                    // Create a loan application
                    LoanApplication loanApplication = new LoanApplication(
                            applicationNumber,
                            requestedAmount,
                            paymentPeriod,
                            "pending",
                            applicationDate,
                            saccoMember.getMemberNumber(), // Set the member number directly in the constructor
                            saccoMember.getSurname(),
                            saccoMember.getFirstName()// Set the first name directly in the constructor
                    );

                    // Insert the loan application into the database
                    if (database.insertLoanApplication(loanApplication)) {
                        out.println("Dear " + saccoMember.getUsername()
                                + ",                                            " + LocalDate.now());
                        out.println(
                                "Congratulations! your loan application has been successfully submitted. We appreciate your interest in our financial services.");
                        out.println("Loan application number: " + applicationNumber);
                        out.println("You will receive an update on the status of your application within 48 hours");
                        out.println(
                                "If you have any questions or need further assistance, please don't hesitate to reach out to our Customer Service Team at +256771895380 or uprizesacco21@gmail.com");
                        out.println("Thank you.");
                        out.println(" ");

                        int accumulatedRequestsCount = database.getAccumulatedRequestsCount();

                        // Check if there are already pending loan applications
                        List<LoanApplication> pendingLoanApplications = database.getPendingLoanApplications();

                        // Check if there are at least ten loan applications accumulated
                        if (accumulatedRequestsCount >= LOAN_REQUEST_THRESHOLD) {
                            // Call the allocateLoans method to allocate loans based on the given criteria
                            if (database.allocateLoans(pendingLoanApplications)) {
                                System.out.println("Loan allocation process completed successfully.");
                                // Call the function to notify the administrator about pending loan applications
                                notifyAdministratorAboutPendingLoans();

                            } else {
                                System.out.println("Failed to allocate loans.");
                            }
                        } else {
                            System.out.println(
                                    "The loan applications will be processed once enough applications are received.");
                        }
                    } else {
                        out.println("Failed to submit loan application. Please try again later.");
                    }
                }
            } catch (NumberFormatException e) {
                out.println("Invalid amount or payment period. Please enter valid numbers.");
            }
        }
    }

    private void notifyAdministratorAboutPendingLoans() {
        // Generate a message for the administrator
        String message = "There are new loan applications pending approval. Please review them in the system.";

        // Insert a notification into the notifications table for the administrator
        boolean notificationInserted = database.insertNotificationForAdministrator(message);

        if (notificationInserted) {
            System.out.println("Administrator notification created successfully.");
        } else {
            System.out.println("Failed to create administrator notification.");
        }
    }

    private void handleLoanRequestStatus(String[] command) throws SQLException {
        if (command.length != 2) {
            out.println("Invalid command. Usage: LoanRequestStatus application_number");
            return;
        }

        String applicationNumber = command[1];
        String loanStatus = database.getLoanStatusByApplicationNumber(applicationNumber);

        if (loanStatus != null) {
            String username = saccoMember != null ? saccoMember.getUsername() : "Member";

            if (loanStatus.equals("pending")) {
                // out.println("Hey " + username + ", your Loan status for application number "
                // + applicationNumber + " is: " + loanStatus);
                out.println("Dear " + username + ",                       Date: " + LocalDate.now());
                out.println("Your loan application of " + applicationNumber + " is currently under review  ("
                        + loanStatus + ") . We appreciate your patience and will notify you once a decision is made.");
                out.println("For any urgent queries, please contact us at +256771895380");
                out.println("Thank you for choosing Uprise sacco.");
                out.println(" ");
            }

            if (loanStatus.equalsIgnoreCase("approved")) {
                handleLoanDecision(applicationNumber);
            } else if (loanStatus.equalsIgnoreCase("rejected")) {
                out.println("Dear " + username + ",");
                out.println("We regret to inform you that your loan application " + applicationNumber
                        + " has been declined.");
                out.println(
                        "If you have any questions or would like further details, please reach out to our Customer Service team at +256771895380 or uprisesacco21@gmail.com");
                out.println("We appreciate your understanding and value your membership.");
                out.println("Thank you.");
                out.println(" ");
            }

        } else {
            out.println("Loan application " + applicationNumber + " not found.");
        }
    }

    private void handleLoanDecision(String applicationNumber) throws SQLException {
        LoanApplication loanApplication = database.getLoanApplicationByApplicationNumber(applicationNumber);
        out.println("<<<< Loan application details >>>> ");
        out.println();
        out.println("Application number: " + loanApplication.getApplicationNumber());
        out.println();
        out.println("Recommended Amount: UGX" + loanApplication.getRequestedAmount());
        out.println();
        out.println("Payment period: " + loanApplication.getPaymentPeriod() + " months");
        out.println();
        out.println("................................................................");
        out.println("Thank you!!!");
        out.println(" ");

        System.out.println("Do you want to accept the loan? (Type 'accept' or 'reject')");
        String decision = readInput();

        if (decision.equalsIgnoreCase("accept")) {
            if (database.acceptLoanApplication(applicationNumber)) {
                LoanApplication acceptedLoanApplication = database
                        .getLoanApplicationByApplicationNumber(applicationNumber);

                if (acceptedLoanApplication != null && processAcceptedLoanApplication(acceptedLoanApplication)) {
                    LoanApplication processedLoan = database.getProcessedLoanDetails(applicationNumber);
                    out.println("................................................................");
                    out.println("<<<<<<<<<<<<<<< Loan Details >>>>>>>>>>>>>>>");
                    out.println();
                    out.println("Date: " + LocalDate.now());
                    out.println();
                    out.println("Application Number: " + processedLoan.getApplicationNumber());
                    out.println();
                    out.println("Amount: UGX" + processedLoan.getLoanAmount());
                    out.println();
                    out.println("Interest Rate: 5% per month");
                    out.println();
                    out.println("Installment Amount: UGX" + processedLoan.getExpectedInstallments()
                            + " (principal + interest)");
                    out.println();
                    out.println("Total Repayment: UGX" + processedLoan.getExpectedLoanRepaymentAmount());
                    out.println();
                    out.println("Repayment Period: " + processedLoan.getPaymentPeriodInMonths() + " months");
                    out.println();
                    out.println("Start Date (repayment): " + processedLoan.getStartDate());
                    out.println();
                    out.println("Cleared Months: " + processedLoan.getClearedMonths());
                    out.println();
                    out.println("Status: " + processedLoan.getStatus());
                    out.println("................................................................");

                    out.println();
                    out.println("Repayment Schedule:");
                    out.println();
                    out.println("Date\t\tRepayment\tInterest\tTotal Payment");
                    out.println();

                    double loanAmount = processedLoan.getLoanAmount();
                    double interestRate = 0.05;
                    double monthlyRepayment = loanAmount / processedLoan.getPaymentPeriodInMonths();

                    LocalDate repaymentDate = processedLoan.getStartDate(); // Use the actual start date

                    double totalMonthlyRepayment = 0.0;
                    double totalInterestPayment = 0.0;
                    double totalTotalPayment = 0.0;

                    for (int month = 1; month <= processedLoan.getPaymentPeriodInMonths(); month++) {
                        double interestPayment = interestRate * monthlyRepayment;
                        double totalPayment = monthlyRepayment + interestPayment;

                        totalMonthlyRepayment += monthlyRepayment;
                        totalInterestPayment += interestPayment;
                        totalTotalPayment += totalPayment;

                        int year = repaymentDate.getYear();
                        int monthValue = repaymentDate.getMonthValue();
                        int day = repaymentDate.getDayOfMonth();

                        out.printf(
                                "%02d.%02d.%d\tUGX%,.2f\tUGX%,.2f\tUGX%,.2f\n",
                                day, monthValue, year, monthlyRepayment, interestPayment, totalPayment);

                        repaymentDate = repaymentDate.plusMonths(1); // Move to the next month
                    }

                    out.println("................................................................");
                    out.printf(
                            "Totals:\t\tUGX%,.2f\tUGX%,.2f\tUGX%,.2f\n",
                            totalMonthlyRepayment, totalInterestPayment, totalTotalPayment);
                    out.println("................................................................");
                    out.println("Loan Disbursement:");
                    out.println("The approved loan amount of UGX" + processedLoan.getLoanAmount()
                            + " will be disbursed to your account on " + LocalDate.now());
                    out.println("Please review the terms of this loan carefully.");
                    out.println(
                            "Should you have any questions or require further clarification, please do not hesitate to contact our loan department at +256771895380 or uprisesacco21@gmail.com");
                    out.println(
                            "We appreciate your membership with Uprise sacco and look forward to supporting your financial journey.");
                    out.println("Thank you.");
                    out.println("................................................................");
                    out.println();
                    out.println(" ");

                } else {
                    out.println("Loan application successfully processed.");
                    System.out.println("Loan application successfully processed.");
                }
            } else {
                out.println("Failed to accept the loan. Please try again or contact support.");
                System.out.println("Failed to accept the loan. Please try again or contact support.");
            }
        } else if (decision.equalsIgnoreCase("reject")) {
            if (database.rejectLoanApplication(applicationNumber)) {
                out.println("Hello " + saccoMember.getUsername() + ", you have successfully declined the loan offer.");
                System.out.println(
                        "Hello " + saccoMember.getUsername() + ", you have successfully rejected the loan offer.");

            } else {
                out.println("Failed to reject the loan. Please try again or contact support.");
                System.out.println("Failed to reject the loan. Please try again or contact support.");
            }
        } else {
            out.println("Invalid decision. Please type 'accept' or 'reject'.");
        }
    }

    private boolean processAcceptedLoanApplication(LoanApplication acceptedLoanApplication) {

        // Calculate expected installments and start date for paying the loan
        int paymentPeriod = acceptedLoanApplication.getPaymentPeriod();
        double loanAmount = acceptedLoanApplication.getRequestedAmount();
        double interestRate = 0.05;

        double totalRepayment = loanAmount * (1 + interestRate);
        double expectedInstallments = totalRepayment / paymentPeriod;
        double expectedLoanRepaymentAmount = expectedInstallments * paymentPeriod;

        // Calculate start date for paying the loan (e.g., one month from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        java.util.Date utilStartDate = calendar.getTime();

        // Convert utilDate to sqlDate
        java.sql.Date startDate = new java.sql.Date(utilStartDate.getTime());

        // Set other fields in the loan table
        String memberId = acceptedLoanApplication.getmemberId();
        String applicationNumber = acceptedLoanApplication.getApplicationNumber();
        String status = "running";
        int loanPaymentPeriod = paymentPeriod;

        // Calculate cleared months and remaining months based on loan deposits
        // Retrieve the amount deposited for the loan application from the
        // loan_repayments table
        double amountDeposited = database.getLoanDepositsTotal(applicationNumber);
        double remainingAmount = expectedLoanRepaymentAmount - amountDeposited;
        int clearedMonths = paymentPeriod - (int) Math.ceil(remainingAmount / expectedInstallments);
        int remainingMonths = paymentPeriod - clearedMonths;

        // Insert the accepted loan application into the loans table
        boolean isLoanInserted = database.insertLoan(
                memberId, applicationNumber, loanAmount, expectedInstallments, expectedLoanRepaymentAmount,
                loanPaymentPeriod, startDate,
                clearedMonths, remainingAmount, remainingMonths, status);

        if (isLoanInserted) {
            System.out.println("Loan inserted successfully.....");
        } else {
            System.out.println("Loan insertion unsuccessfully.....");
        }

        return isLoanInserted; // Return whether the insertion was successful
    }

    private void performAutomaticDeductions(String memberId) {
        // Get the active loan for the logged-in member that needs deductions
        Loan activeLoan = database.getActiveLoanWithPendingInstallmentsForMember(memberId);

        if (activeLoan != null) {
            // Calculate the installment amount to deduct
            double installmentAmount = activeLoan.getExpectedInstallmentAmount();

            // Get the member's available deposit
            double availableDeposit = database.getAvailableDepositForMember(memberId);

            if (availableDeposit >= installmentAmount) {
                // Deduct the installment from the member's deposit
                boolean deductionSuccessful = database.deductLoanInstallment(memberId, installmentAmount);

                if (deductionSuccessful) {
                    // Update the loan details with cleared and remaining months
                    activeLoan.setClearedMonths(activeLoan.getClearedMonths() + 1);
                    activeLoan.setRemainingMonths(activeLoan.getRemainingMonths() - 1);
                    activeLoan.setRemainingAmount(activeLoan.getRemainingAmount() - installmentAmount);

                    // Update the loan status if all installments are paid
                    if (activeLoan.getRemainingMonths() == 0) {
                        activeLoan.setStatus("completed");
                    }

                    // Update the loan record in the database
                    database.updateLoanDetails(activeLoan);

                    // Record the installment payment
                    database.recordInstallmentPayment(memberId, activeLoan.getApplicationNumber(), installmentAmount);

                    out.println("            Loan Repayment Successful!");
                    out.println("-----------------------------------------------------");
                    out.println("You have successfully repaid UGX" + String.format("%.2f", installmentAmount)
                            + " towards your loan.");
                    out.println();
                    out.println("Cleared Months: " + activeLoan.getClearedMonths());
                    out.println();
                    out.println("Remaining Months: " + activeLoan.getRemainingMonths());
                    out.println();
                    out.println("Remaining Amount: UGX" + String.format("%.2f", activeLoan.getRemainingAmount()));
                    out.println();
                    out.println("Thank you for your loan repayment!");
                    out.println("-----------------------------------------------------");
                    out.println(" ");

                    System.out.println("Loan Repayment Successful!");
                    System.out.println("----------------------");
                    System.out.println("Cleared Months: " + activeLoan.getClearedMonths());
                    System.out.println("Remaining Months: " + activeLoan.getRemainingMonths());
                    System.out
                            .println("Remaining Amount: UGX" + String.format("%.2f", activeLoan.getRemainingAmount()));
                    System.out.println("Thank you for your loan repayment!");
                    System.out.println("----------------------");

                } else {
                    out.println("Failed to deduct installment for member ");
                    System.out.println("Failed to deduct installment for member " + memberId);
                }
            } else {
                System.out.println("Insufficient funds for member " + memberId + " to deduct installment.");
            }
        } else {
            out.println("Sorry! you have No active loan with pending installments");
            System.out.println("No active loan with pending installments for member " + memberId);
        }
    }

    private void handleCheckStatement(String[] command) {
        if (command.length != 3) {
            out.println("Invalid command. Usage: CheckStatement dateFrom dateTo");
            return;
        }

        LocalDate dateFrom = LocalDate.parse(command[1]);
        LocalDate dateTo = LocalDate.parse(command[2]);

        boolean hasLoan = database.getLoanForAMember(saccoMember.getMemberNumber());

        if (hasLoan) {

            List<LoanPayment> loanPayments = database.getLoanPaymentsBetweenDates(saccoMember.getMemberNumber(),
                    dateFrom, dateTo);
            List<Contribution> contributions = database.getContributionsBetweenDates(saccoMember.getMemberNumber(),
                    dateFrom, dateTo);

            double loanProgress = calculateLoanProgress(loanPayments);
            double contributionProgress = calculateContributionProgress(contributions, dateFrom, dateTo);

            double memberPerformance = (loanProgress + contributionProgress) / 2; // Calculate the average of loan and
                                                                                  // contribution progress

            double AverageSaccoPerformance = calculateSaccoOverallPerformance();

            out.println("Dear " + saccoMember.getUsername() + "             Date: " + LocalDate.now());
            out.println("your account Statement from " + dateFrom + " to " + dateTo + ":");
            out.println("--------------------------------------------------------------");
            out.println();

            out.println("Loan Payments:");
            for (LoanPayment payment : loanPayments) {
                out.println(payment.getDate() + " - Amount: UGX" + String.format("%.2f", payment.getAmount()));
            }
            out.println("Loan Progress: " + String.format("%.2f", loanProgress) + "%");
            out.println();

            out.println("Contributions:");
            for (Contribution contribution : contributions) {
                out.println(
                        contribution.getDate() + " - Amount: UGX" + String.format("%.2f", contribution.getAmount()));
            }
            out.println("Contribution Progress: " + String.format("%.2f", contributionProgress) + "%");
            out.println();

            out.println("Your Performance: " + String.format("%.2f", memberPerformance) + "%");
            out.println();

            out.println("Overall Sacco Performance: " + AverageSaccoPerformance + "%");

            out.println("--------------------------------------------------------------");
            out.println("Thank You!");
            out.println(" ");

        } else {
            List<Contribution> contributions = database.getContributionsBetweenDates(saccoMember.getMemberNumber(),
                    dateFrom, dateTo);
            double contributionProgress = calculateContributionProgress(contributions, dateFrom, dateTo);
            double memberPerformance = contributionProgress;
            double AverageSaccoPerformance = calculateSaccoOverallPerformance();

            out.println("Dear " + saccoMember.getUsername() + "             Date: " + LocalDate.now());
            out.println("Your account Statement from " + dateFrom + " to " + dateTo + ":");
            out.println("--------------------------------------------------------------");
            out.println();

            out.println("You have no running loan currently.");
            out.println();
            out.println("Contributions:");
            for (Contribution contribution : contributions) {
                out.println(
                        contribution.getDate() + " - Amount: UGX" + String.format("%.2f", contribution.getAmount()));
            }
            out.println("Contribution Progress: " + String.format("%.2f", contributionProgress) + "%");
            out.println();

            out.println("Your Performance: " + String.format("%.2f", memberPerformance) + "%");
            out.println();

            out.println("Overall Sacco Performance: " + AverageSaccoPerformance + "%");

            out.println("--------------------------------------------------------------");
            out.println("Thank You!");
            out.println(" ");
        }

    }

    private double calculateLoanProgress(List<LoanPayment> loans) {
        if (loans == null || loans.isEmpty()) {
            return 0;
        }

        // Calculate total cleared months and total expected months across all loans
        int totalClearedMonths = 0;
        int totalExpectedMonths = 0;

        for (LoanPayment loan : loans) {
            totalClearedMonths += loan.getClearedMonths();
            totalExpectedMonths += loan.getPaymentPeriod();
        }

        // Calculate loan progress percentage
        double loanProgress = (double) totalClearedMonths / totalExpectedMonths * 100;

        return loanProgress;
    }

    private double calculateContributionProgress(List<Contribution> contributions, LocalDate dateFrom,
            LocalDate dateTo) {
        // Calculate contribution progress based on total contributions and expected
        // contributions
        // Get the member's expected contribution amount for the specified time frame
        double expectedContributionAmount = calculateExpectedContributionAmount(dateFrom, dateTo);

        // Calculate the actual total contributions made by the member within the time
        // frame
        double totalActualContributions = calculateTotalActualContributions(contributions, dateFrom, dateTo);

        // Calculate contribution progress percentage
        double contributionProgressPercentage = (totalActualContributions / expectedContributionAmount) * 100;

        return contributionProgressPercentage;
    }

    private double calculateExpectedContributionAmount(LocalDate dateFrom, LocalDate dateTo) {
        // Calculate the number of contribution intervals (e.g., months) within the
        // specified time frame
        long numberOfIntervals = ChronoUnit.MONTHS.between(dateFrom, dateTo) + 1; // +1 to include the end month

        // Get the member's expected contribution amount per interval (e.g., monthly
        // contribution amount)
        double expectedIntervalContribution = saccoMember.getMonthlyContributionAmount();

        // Calculate the expected total contribution amount
        double expectedTotalContributions = numberOfIntervals * expectedIntervalContribution;

        return expectedTotalContributions;
    }

    private double calculateTotalActualContributions(List<Contribution> contributions, LocalDate dateFrom,
            LocalDate dateTo) {
        // Calculate the actual total contributions made by the member within the time
        // frame
        double totalActualContributions = 0.0;

        for (Contribution contribution : contributions) {
            LocalDate contributionDate = contribution.getDate();

            // Check if the contribution date is within the specified time frame
            if (contributionDate.isEqual(dateFrom) || contributionDate.isEqual(dateTo)
                    || (contributionDate.isAfter(dateFrom) && contributionDate.isBefore(dateTo))) {
                totalActualContributions += contribution.getAmount();
            }
        }

        return totalActualContributions;
    }

    private double calculateSaccoOverallPerformance() {
        // Get all Sacco members
        int saccoMembers = database.getAllSaccoMembersCount();
        List<LocalDate> depositDates = database.getDepositDates();
        int numberOfMonths = 0;

        if (!depositDates.isEmpty()) {
            LocalDate lastDate = depositDates.get(depositDates.size() - 1);
            LocalDate firstDate = depositDates.get(0);

            Period period = Period.between(firstDate.withDayOfMonth(1), lastDate.withDayOfMonth(1));
            numberOfMonths = period.getYears() * 12 + period.getMonths() + 1; // +1 to include the last month
        }

        System.out.println("Number of members: " + saccoMembers);
        System.out.println("Number of months: " + numberOfMonths);

        double TotalActualContributions = database.getTotalActualContributions();
        System.out.println("total actual contributions: " + TotalActualContributions);

        double ExpectedMonthlyContribution = saccoMembers * saccoMember.getMonthlyContributionAmount();
        System.out.println("Expected monthly contribution: " + ExpectedMonthlyContribution);

        double TotalExpectedContributions = numberOfMonths * ExpectedMonthlyContribution;
        System.out.println("Total expected contribution: " + TotalExpectedContributions);

        double ContributionProgress = (TotalActualContributions / TotalExpectedContributions) * 100;
        System.out.println("Contribution progress: " + ContributionProgress);

        // Calculate total loan repayments received
        double totalLoanRepaymentsReceived = database.getTotalLoanRepayments();
        System.out.println("Total loan repayments: " + totalLoanRepaymentsReceived);

        // Calculate loan repayment rate percentage
        double TotalExpectedLoanRepaymentAmount = database.getExpectedLoanRepaymentAmount();
        System.out.println("Total expected loan repayment amount: " + TotalExpectedLoanRepaymentAmount);

        double loanRepaymentRate = (totalLoanRepaymentsReceived / TotalExpectedLoanRepaymentAmount) * 100;
        System.out.println("Loan repayment rate: " + loanRepaymentRate);

        // Calculate the overall average performance
        double AverageSaccoPerformance = (ContributionProgress + loanRepaymentRate) / 2;
        System.out.println("Overall sacco performance: " + AverageSaccoPerformance);

        return AverageSaccoPerformance;
    }

    private double calculateLoanProgressForSpecificMember(String memberNumber) {
        double loanPayments = database.getLoanPaymentsForMember(memberNumber);
        System.out.println("Loan payments: " + loanPayments);

        double expectedLoanRepaymentAmount = database.getExpectedLoanRepaymentAmount(memberNumber);
        System.out.println("Expected loan repayment amount: " + expectedLoanRepaymentAmount);

        double loanProgress = (loanPayments / expectedLoanRepaymentAmount) * 100;
        System.out.println("loan progress: " + loanProgress);

        return loanProgress;

    }

    private double calculateContributionProgress(String memberNumber) {
        double TotalDepositsMade = database.getContributionsForAMember(memberNumber);
        System.out.println("Total deposits made: " + TotalDepositsMade);

        List<LocalDate> depositDates = database.getDepositDatesForAMember(memberNumber);
        int numberOfMonths = 0;

        if (!depositDates.isEmpty()) {
            LocalDate lastDate = depositDates.get(depositDates.size() - 1);
            LocalDate firstDate = depositDates.get(0);

            Period period = Period.between(firstDate.withDayOfMonth(1), lastDate.withDayOfMonth(1));
            numberOfMonths = period.getYears() * 12 + period.getMonths() + 1; // +1 to include the last month
        }
        System.out.println("Number of months: " + numberOfMonths);

        double TotalExpectedDeposits = saccoMember.getMonthlyContributionAmount() * numberOfMonths;
        System.out.println("Expected deposits: " + TotalExpectedDeposits);

        double contributionProgress = (TotalDepositsMade / TotalExpectedDeposits) * 100;
        System.out.println("Contribution progress: " + contributionProgress);

        return contributionProgress;

    }

}
