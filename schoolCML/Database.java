import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/sacco";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Method to establish a database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean isValidCredentials(String username, String password) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM members WHERE username = ? AND password = ?")) {
    
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    public boolean isValidCredential(String username, String randomPassword) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM members WHERE username = ? AND randomPassword = ?")) {
    
            stmt.setString(1, username);
            stmt.setString(2, randomPassword);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    public String getMemberNumberByUsername(String username) {
      
        String memberNumber = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            
            String sql = "SELECT memberId FROM members WHERE username = ?";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                memberNumber = resultSet.getString("memberId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return memberNumber;
    }

    
    public String getSurnameByUsername(String username) {
      
        String surname = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            
            String sql = "SELECT surname FROM members WHERE username = ?";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                surname = resultSet.getString("surname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return surname;
    }

    public String getFirstNameByUsername(String username) {
      
        String firstName = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            
            String sql = "SELECT firstName FROM members WHERE username = ?";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                firstName = resultSet.getString("firstName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return firstName;
    }
     public String getGenderByUsername(String username) {
      
        String gender = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            
            String sql = "SELECT gender FROM members WHERE username = ?";
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                gender = resultSet.getString("gender");
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return gender;
    }

    public boolean checkMemberNumberAndPhoneNumberInDatabase(String memberNumber, String phoneNumber) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM members WHERE memberId = ? AND contact = ?")) {

            stmt.setString(1, memberNumber);
            stmt.setString(2, phoneNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateMemberRandomPassword(String memberNumber, String randomPassword) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE members SET randomPassword = ? WHERE memberId = ?")) {
    
            stmt.setString(1, randomPassword);
            stmt.setString(2, memberNumber);
            stmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean insertLoanApplication(LoanApplication loanApplication) {
       
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO loan_application (application_number, memberId, surname, firstName, amount, paymentPeriod, status, application_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            // Set the parameters in the prepared statement
            preparedStatement.setString(1, loanApplication.getApplicationNumber());
            preparedStatement.setString(2, loanApplication.getmemberId());
            preparedStatement.setString(3, loanApplication.getSurname());
            preparedStatement.setString(4, loanApplication.getFirstName());
            preparedStatement.setDouble(5, loanApplication.getRequestedAmount());
            preparedStatement.setInt(6, loanApplication.getPaymentPeriod());
            preparedStatement.setString(7, loanApplication.getStatus());

             // Format the date to insert into the database
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(loanApplication.getApplicationDate());
            preparedStatement.setString(8, formattedDate);

            // Execute the SQL query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If failed
        }
    }

    public boolean hasPendingLoanApplications(String memberNumber) {
        String query = "SELECT COUNT(*) FROM loan_application WHERE memberId = ? AND status = 'pending'";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the memberNumber as a parameter in the query
            statement.setString(1, memberNumber);
        
            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();
        
            // Get the count of pending loan applications
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Return false in case of an error or no pending loans found
    }
    
    public int getAccumulatedRequestsCount() {
        int count = 0;
    
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT COUNT(*) FROM loan_application WHERE status = 'pending'";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return count;
    }
   
    public List<LoanApplication> getPendingLoanApplications(String memberId) {
        List<LoanApplication> pendingLoans = new ArrayList<>();
    
        String query = "SELECT application_number, surname, firstName, amount, paymentPeriod, status, application_date FROM loan_application WHERE memberId = ? AND status = 'pending'";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the memberId as a parameter in the query
            statement.setString(1, memberId);
    
            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();
    
            // Iterate through the result set and create LoanApplication objects
            while (resultSet.next()) {
                String applicationNumber = resultSet.getString("application_number");
                String surname = resultSet.getString("surname");
                String firstName = resultSet.getString("firstName");
                double requestedAmount = resultSet.getDouble("amount");
                int paymentPeriod = resultSet.getInt("paymentPeriod");
                String status = resultSet.getString("status");
    
                // Retrieve the application date directly as a java.util.Date from the result set
                Date applicationDate = resultSet.getDate("application_date");
    
                // Create a LoanApplication object and add it to the list of pending loans
                LoanApplication loanApplication = new LoanApplication(applicationNumber, requestedAmount, paymentPeriod, status, applicationDate, memberId, surname, firstName);
                pendingLoans.add(loanApplication);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return pendingLoans;
    }
    
    public List<LoanApplication> getPendingLoanApplications() {
        List<LoanApplication> pendingLoanApplications = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM loan_application WHERE status = 'pending' LIMIT 10");
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                String applicationNumber = resultSet.getString("application_number");
                String surname = resultSet.getString("surname");
                String firstName = resultSet.getString("firstName");
                String memberId = resultSet.getString("memberId");
                double requestedAmount = resultSet.getDouble("amount");
                int paymentPeriod = resultSet.getInt("paymentPeriod");
                String status = resultSet.getString("status");
                Date applicationDate = resultSet.getDate("application_date");

                // Create a new LoanApplication object and add it to the list
                pendingLoanApplications.add(new LoanApplication(applicationNumber, requestedAmount, paymentPeriod, status, applicationDate, memberId, surname, firstName));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingLoanApplications;
    }


    public boolean isValidReceiptNumber(String receiptNumber, double amount, String dateDeposited, String memberId) {
        String query = "SELECT COUNT(*) FROM deposits WHERE receiptNo = ? AND amount = ? AND date = ? AND memberId = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, receiptNumber);
            statement.setDouble(2, amount);
            statement.setString(3, dateDeposited);
            statement.setString(4, memberId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // If count is greater than 0, receipt number is valid
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Error occurred or receipt number is not valid
    }

 public boolean allocateLoans(List<LoanApplication> loanApplications) {
    double availableFunds = getAvailableFunds();

    if (availableFunds == 0) {
        // Handle insufficient funds
        for (LoanApplication loanApplication : loanApplications) {
            sendInsufficientFundsMessage(loanApplication.getmemberId());
        }
        return false;
    }

    for (LoanApplication loanApplication : loanApplications) {
        String memberId = loanApplication.getmemberId();
        double requestedAmount = loanApplication.getRequestedAmount();
        double maxAllowedAmount = (0.75 * loanApplication.getContributionsForMember(memberId));
        double totalRequestedAmount = 0;

        // Calculate the total requested amount for all loan applications
        for (LoanApplication app : loanApplications) {
            totalRequestedAmount += app.getRequestedAmount();
        }

        if (totalRequestedAmount <= availableFunds) {
            if (requestedAmount <= maxAllowedAmount) {
                // Approve the loan within the allowed limit
                loanApplication.setStatus("Waiting approval");
                loanApplication.setRequestedAmount(requestedAmount);
                if (!updateLoanApplication(loanApplication)) {
                    return false;
                }
                sendLoanAcceptedMessage(memberId, loanApplication.getApplicationNumber());
            } else {
                // Adjust the requested amount to be within the allowed limit
                requestedAmount = maxAllowedAmount;
                loanApplication.setRequestedAmount(requestedAmount);
                loanApplication.setStatus("Waiting approval");
                if (!updateLoanApplication(loanApplication)) {
                    return false;
                }
                sendLoanAcceptedMessage(memberId, loanApplication.getApplicationNumber());
            }
        } else if (totalRequestedAmount > availableFunds) {
            double equitableShare = (requestedAmount / totalRequestedAmount) * availableFunds;
            double allocatedAmount = Math.min(maxAllowedAmount, equitableShare);

            if (allocatedAmount <= 0) {
                // Reject the loan due to insufficient funds
                loanApplication.setStatus("rejected");
                if (!updateLoanApplication(loanApplication)) {
                    return false;
                }
                sendInsufficientFundsMessage(memberId);
            } else {
                loanApplication.setStatus("Waiting approval");
                loanApplication.setRequestedAmount(allocatedAmount);
                if (!updateLoanApplication(loanApplication)) {
                    return false;
                }
                sendLoanAcceptedMessage(memberId, loanApplication.getApplicationNumber());
                availableFunds -= allocatedAmount;
            }
        }
    }

    return true;
}

public void sendLoanAcceptedMessage(String memberNumber, String applicationNumber) {
    //String username = loanApplication.getUsername();
    String message = "Dear we're pleased to inform you that your loan application with application number " + applicationNumber + " has been successfully processed and is now awaiting approval by the administrator.";
    storeMessage(memberNumber, message);
} 


public double getContributionsForMember(String memberNumber) {
    double contributions = 0.0;
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement("SELECT SUM(amount) AS total_Contribution FROM deposits WHERE memberId = ?")) {

        stmt.setString(1, memberNumber);
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                contributions = resultSet.getDouble("total_Contribution");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return contributions;
}

public void storeMessage(String memberNumber, String message) {
    String query = "INSERT INTO member_messages (memberId, message, timestamp) VALUES (?, ?, NOW())";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, memberNumber);
        statement.setString(2, message);
        
        int rowsAffected = statement.executeUpdate();
        
        if (rowsAffected == 1) {
            System.out.println("Message stored successfully.");
        } else {
            System.out.println("Failed to store message.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Method to send a message to the member indicating insufficient funds.
private static void sendInsufficientFundsMessage(String memberId) {
    String message;
    String username = getMemberUsername(memberId); // Fetch the username from the database

    if (username != null) {
        message = "Dear " + username + ", we regret to inform you that there are insufficient funds to process your loan request at the moment. Please try again later.";
    } else {
        // In case the username is not found, use a generic message without the username
        message = "We regret to inform you that there are insufficient funds to process your loan request at the moment. Please try again later.";
    }

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String insertQuery = "INSERT INTO member_messages (memberId, message, timestamp) VALUES (?, ?, NOW())";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, message);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Message stored successfully.");
            } else {
                System.out.println("Failed to store message.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Method to fetch the username from the database based on the memberId
private static String getMemberUsername(String memberId) {
    String username = null;
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String selectQuery = "SELECT username FROM members WHERE memberId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    username = rs.getString("username");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return username;
}

public List<String> getMessagesForMember(String memberNumber) {
    List<String> messages = new ArrayList<>();
    String query = "SELECT message FROM member_messages WHERE memberId = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, memberNumber);

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String message = resultSet.getString("message");
                messages.add(message);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return messages;
}

public boolean insertNotificationForAdministrator(String message) {
    // Define SQL query to insert a notification into the notifications table
    String insertQuery = "INSERT INTO notifications (message, status, timestamp) VALUES (?, 'unread', NOW())";

    try (PreparedStatement preparedStatement = getConnection().prepareStatement(insertQuery)) {
        // Set parameters for the prepared statement
        preparedStatement.setString(1, message);

        // Execute the insert query
        int rowsAffected = preparedStatement.executeUpdate();

        // Return true if at least one row was affected (notification inserted)
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false; 
    }
}
  
    // Helper method to get the available funds in the Sacco
    private double getAvailableFunds() {
        double availableFunds = 0;
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT SUM(amount) AS available_Funds FROM deposits");
             ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                availableFunds = resultSet.getDouble("available_Funds");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Subtract the minimum threshold of 2000000
        availableFunds -= 2000000;
        
        // Ensure the available funds are not negative
        if (availableFunds < 0) {
            availableFunds = 0;
        }
        
        return availableFunds;
    }
    

    // Method to update the loan application in the database
    private boolean updateLoanApplication(LoanApplication loanApplication) {
       
         try (Connection connection = getConnection();
              PreparedStatement stmt = connection.prepareStatement("UPDATE loan_application " +
                      "SET status = ?, amount = ? " +
                      "WHERE application_number = ?")) {
             stmt.setString(1, loanApplication.getStatus());
             stmt.setDouble(2, loanApplication.getRequestedAmount());
             stmt.setString(3, loanApplication.getApplicationNumber());
             int rowsAffected = stmt.executeUpdate();
             return rowsAffected > 0;
         } catch (SQLException e) {
             e.printStackTrace();
             return false;
         }
       
    }

    // Method to get loan applications for a specific member from the database
    public List<LoanApplication> getLoanApplicationsForMember(String memberId) {
        List<LoanApplication> loanApplications = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM loan_application WHERE memberId = ?")) {
            stmt.setString(1, memberId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String applicationNumber = resultSet.getString("application_number");
                String surname = resultSet.getString("surname");
                String firstName = resultSet.getString("first_name");
                double requestedAmount = resultSet.getDouble("amount");
                int paymentPeriod = resultSet.getInt("paymentPeriod");
                String status = resultSet.getString("status");
                Date applicationDate = resultSet.getDate("application_date");
                // Create a new LoanApplication object and add it to the list
                LoanApplication loanApplication = new LoanApplication(applicationNumber, requestedAmount, paymentPeriod, status, applicationDate, memberId, surname, firstName);
                loanApplications.add(loanApplication);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanApplications;
    }
    
    public String getLoanStatusByApplicationNumber(String applicationNumber) {
        String loanStatus = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT status FROM loan_application WHERE application_number = ?")) {
            preparedStatement.setString(1, applicationNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // The application number was found, get the loan status
                    loanStatus = resultSet.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanStatus;
    }

    public LoanApplication getLoanApplicationByApplicationNumber(String applicationNumber) throws SQLException {
        Connection connection = getConnection();
    
        String sql = "SELECT * FROM loan_application WHERE application_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, applicationNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String memberId = resultSet.getString("memberId");
                    String surname = resultSet.getString("surname");
                    String firstName = resultSet.getString("firstName");
                    double requestedAmount = resultSet.getDouble("amount");
                    int paymentPeriod = resultSet.getInt("paymentPeriod");
                    String status = resultSet.getString("status");
                    java.sql.Date sqlApplicationDate = resultSet.getDate("application_date");
                    Date applicationDate = new Date(sqlApplicationDate.getTime());
    
                    // Create and return a LoanApplication instance
                    return new LoanApplication(applicationNumber, requestedAmount, paymentPeriod, status, applicationDate, memberId, surname, firstName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null; 
    }
    
    

    // Method to accept a loan application and update its status to "accepted"
    public boolean acceptLoanApplication(String applicationNumber)  {
       try {
        Connection connection = getConnection();
        String upDateQuery = "UPDATE loan_application SET status = 'accepted' WHERE application_number = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(upDateQuery);
        preparedStatement.setString(1, applicationNumber);

        int rowsAffected = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return rowsAffected > 0;
       } catch (Exception e) {
        return false;
       }
    }

    // Method to reject a loan application and update its status to "rejected"
    public boolean rejectLoanApplication(String applicationNumber) {
       try {
        Connection connection = getConnection();
        String upDateQuery = "UPDATE loan_application SET status = 'rejected' WHERE application_number = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(upDateQuery);
        preparedStatement. setString(1, applicationNumber);

        int rowsAffected = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return rowsAffected > 0;
       } catch (Exception e) {
        return false;
       } 
    }   

    public double getLoanDepositsTotal(String applicationNumber) {
        double totalDeposits = 0.0;
    
        String query = "SELECT SUM(amount_deposited) AS total_deposits FROM loan_repayments WHERE application_number = ?";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, applicationNumber);
    
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                totalDeposits = resultSet.getDouble("total_deposits");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return totalDeposits;
    }
    

    public boolean insertLoan(String memberId, String applicationNumber, double loanAmount,
            double expectedInstallments, double expectedLoanRepaymentAmount, int loanPaymentPeriod, Date startDate, int cleared_months, double remaining_amount, int remaining_months, String status ) {
        String query = "INSERT INTO loans (memberId, application_number, loan_amount, expected_installments, expectedLoanRepaymentAmount, loanPaymentPeriodInMonths, start_date, cleared_months, remaining_amount, remaining_months, status) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, memberId);
        statement.setString(2, applicationNumber);
        statement.setDouble(3, loanAmount);
        statement.setDouble(4, expectedInstallments);
        statement.setDouble(5, expectedLoanRepaymentAmount);
        statement.setInt(6, loanPaymentPeriod);
        statement.setDate(7, startDate);
        statement.setInt(8, cleared_months);
        statement.setDouble(9, remaining_amount);
        statement.setInt(10, remaining_months);
        statement.setString(11, status);
        //statement.setString(12, surname);
        //statement.setString(13, firstName);
       
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }
   }

    public List<LoanPayment> getLoanPaymentsBetweenDates(String memberNumber, LocalDate dateFrom, LocalDate dateTo) {
    List<LoanPayment> loanPayments = new ArrayList<>();

    String query = "SELECT application_number, date, amount_deposited FROM loan_repayments WHERE memberId = ? AND date BETWEEN ? AND ?";
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, memberNumber);
        statement.setDate(2, Date.valueOf(dateFrom));
        statement.setDate(3, Date.valueOf(dateTo));

        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            LocalDate date = resultSet.getDate("date").toLocalDate();
            double amount = resultSet.getDouble("amount");
            //String status = resultSet.getString("status");
            
            LoanPayment loanPayment = new LoanPayment(date, amount);
            loanPayments.add(loanPayment);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return loanPayments;
}

public List<LoanPayment> getLoanByMemberNumber(String memberNumber) {
    List<LoanPayment> loans = new ArrayList<>();

    String query = "SELECT loanPaymentPeriodInMonths, cleared_months, FROM loans WHERE memberId = ?";
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, memberNumber);

        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            int paymentPeriod = resultSet.getInt("loanPaymentPeriodInMonths");
            int clearedMonths = resultSet.getInt("cleared_months");
            
            LoanPayment loan = new LoanPayment(paymentPeriod, clearedMonths);
            loans.add(loan);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return loans;
}


public List<Contribution> getContributionsBetweenDates(String memberNumber, LocalDate dateFrom, LocalDate dateTo) {
    List<Contribution> contributions = new ArrayList<>();

    String query = "SELECT date, amount FROM deposits WHERE memberId = ? AND date BETWEEN ? AND ?";
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, memberNumber);
        statement.setDate(2, Date.valueOf(dateFrom));
        statement.setDate(3, Date.valueOf(dateTo));

        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            LocalDate date = resultSet.getDate("date").toLocalDate();
            double amount = resultSet.getDouble("amount");
            
            Contribution contribution = new Contribution(date, amount);
            contributions.add(contribution);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return contributions;
}

public int getAllSaccoMembersCount() {
    int memberCount = 0;

    String query = "SELECT COUNT(*) AS memberCount FROM members";
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            memberCount = resultSet.getInt("memberCount");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return memberCount;
}

    
    public double getTotalLoanRepayments() {
        double totalRepayments = 0.0;
    
        String query = "SELECT SUM(amount_deposited) AS total_repayments FROM loan_repayments";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                totalRepayments = resultSet.getDouble("total_repayments");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return totalRepayments;
    }
    
    public double getExpectedLoanRepaymentAmount() {
        double TotalExpectedLoanRepaymentAmount = 0.0;
    
        String query = "SELECT SUM(expectedLoanRepaymentAmount) AS TotalExpectedLoanRepaymentAmount FROM loans";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                TotalExpectedLoanRepaymentAmount = resultSet.getDouble("TotalExpectedLoanRepaymentAmount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return TotalExpectedLoanRepaymentAmount;
    }

    public List<LocalDate> getDepositDates() {
        List<LocalDate> depositDates = new ArrayList<>();
    
        String query = "SELECT DISTINCT date FROM deposits";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                LocalDate depositDate = resultSet.getDate("date").toLocalDate();
                depositDates.add(depositDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return depositDates;
    }
    
    public double getTotalActualContributions() {
        double TotalActualContributions = 0.0;
    
        String query = "SELECT SUM(amount) AS TotalActualContributions FROM deposits";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                TotalActualContributions = resultSet.getDouble("TotalActualContributions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return TotalActualContributions;
    }

    public double getLoanPaymentsForMember(String memberNumber) {
        double loanPayments = 0.0;

    String query = "SELECT SUM(amount_deposited) AS total_loanPayments FROM loan_repayments WHERE memberId = ?";
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, memberNumber);
        
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            loanPayments = resultSet.getDouble("total_loanPayments");
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return loanPayments;
}

public double getExpectedLoanRepaymentAmount(String memberNumber){
    double expectedLoanRepaymentAmount = 0.0;

    String query = "SELECT Sum(expectedLoanRepaymentAmount) AS Total_ExpectedLoanRepayment_Amount FROM loans WHERE memberId = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, memberNumber);
        
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            expectedLoanRepaymentAmount = resultSet.getDouble("Total_ExpectedLoanRepayment_Amount");
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return expectedLoanRepaymentAmount;
}
            
public boolean getLoanForAMember(String memberNumber) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM loans WHERE memberId = ? AND status = 'running' ")) {
    
            stmt.setString(1, memberNumber);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    public double getContributionsForAMember(String memberNumber){
    double totalDepositsMade = 0.0;

    String query = "SELECT Sum(amount) AS total_deposits FROM deposits WHERE memberId = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, memberNumber);
        
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            totalDepositsMade = resultSet.getDouble("total_deposits");
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return totalDepositsMade;
}

public List<LocalDate> getDepositDatesForAMember(String memberNumber) {
        List<LocalDate> depositDates = new ArrayList<>();
    
        String query = "SELECT DISTINCT date FROM deposits WHERE memberId = ?";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setString(1, memberNumber);

            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                LocalDate depositDate = resultSet.getDate("date").toLocalDate();
                depositDates.add(depositDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return depositDates;
    }
    /* public LoanApplication getProcessedLoanDetails(String applicationNumber) throws SQLException {
        Connection connection = getConnection();
    
        String sql = "SELECT * FROM loans WHERE application_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, applicationNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double loanAmount = resultSet.getDouble("loan_amount");
                    double expected_installments = resultSet.getDouble("expected_installments");
                    double expectedLoanRepaymentAmount = resultSet.getDouble("expectedLoanRepaymentAmount");
                    int paymentPeriodInMonths = resultSet.getInt("loanPaymentPeriodInMonths");
                    int cleared_months = resultSet.getInt("cleared_months");
                    java.sql.Date sqlApplicationDate = resultSet.getDate("start_date");
                    LocalDate start_date = new LocalDate(sqlApplicationDate.getTime());
                    String status = resultSet.getString("status");
    
                    // Create and return a Loan instance
                    return new LoanApplication(applicationNumber, loanAmount, expected_installments, expectedLoanRepaymentAmount, paymentPeriodInMonths, cleared_months, start_date, status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null; 
    }*/
    
    

    public LoanApplication getProcessedLoanDetails(String applicationNumber) throws SQLException {
        Connection connection = getConnection();
    
        String sql = "SELECT * FROM loans WHERE application_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, applicationNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double loanAmount = resultSet.getDouble("loan_amount");
                    double expected_installments = resultSet.getDouble("expected_installments");
                    double expectedLoanRepaymentAmount = resultSet.getDouble("expectedLoanRepaymentAmount");
                    int paymentPeriodInMonths = resultSet.getInt("loanPaymentPeriodInMonths");
                    int cleared_months = resultSet.getInt("cleared_months");
                    java.sql.Date sqlApplicationDate = resultSet.getDate("start_date");
                    LocalDate start_date = sqlApplicationDate.toLocalDate(); // Corrected line
                    String status = resultSet.getString("status");
    
                    // Create and return a Loan instance
                    return new LoanApplication(applicationNumber, loanAmount, expected_installments, expectedLoanRepaymentAmount, paymentPeriodInMonths, cleared_months, start_date, status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null; 
    }
    
    public Loan getActiveLoanWithPendingInstallmentsForMember(String memberId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM loans WHERE memberId = ? AND status = 'running' AND remaining_months > 0")) {
            statement.setString(1, memberId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Loan loan = new Loan();
                    loan.setMemberId(resultSet.getString("memberId"));
                    loan.setApplicationNumber(resultSet.getString("application_number"));
                    loan.setExpectedInstallmentAmount(resultSet.getDouble("expected_installments"));
                    loan.setClearedMonths(resultSet.getInt("cleared_months"));
                    loan.setRemainingMonths(resultSet.getInt("remaining_months"));
                    loan.setStatus(resultSet.getString("status"));
                    loan.setRemainingAmount(resultSet.getDouble("remaining_amount"));
                
                    return loan;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null; // No active loan with pending installments found for the member
    }
    
    
    public double getAvailableDepositForMember(String memberId) {
        double availableDeposit = 0.0;
    
        try {
            Connection connection = getConnection(); 
            
            String query = "SELECT SUM(amount) AS total_deposit FROM deposits WHERE memberId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, memberId);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                availableDeposit = resultSet.getDouble("total_deposit");
            }
            
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return availableDeposit;
    }
    
    public boolean deductLoanInstallment(String memberId, double installmentAmount) {
        try {
            Connection connection = getConnection(); // Implement your database connection logic here
            
            // Check if the member has enough available deposit to cover the installment
            double availableDeposit = getAvailableDepositForMember(memberId);
            if (availableDeposit < installmentAmount) {
                return false; // Insufficient balance for deduction
            }
            
            // Update the deposits table to deduct the installment amount
            String updateQuery = "UPDATE deposits SET amount = amount - ? WHERE memberId = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDouble(1, installmentAmount);
            updateStatement.setString(2, memberId);
            
            int rowsAffected = updateStatement.executeUpdate();
            
            updateStatement.close();
            connection.close();
            
            return rowsAffected > 0; // Return true if rows were updated (deduction successful)
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurred
        }
    }
    
    public boolean updateLoanDetails(Loan loan) {
        try {
            Connection connection = getConnection();
            
            // Update the loan record in the loans table
            String updateQuery = "UPDATE loans SET cleared_months = ?, remaining_amount = ?, remaining_months = ?, status = ?  WHERE memberId = ?";  
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, loan.getClearedMonths());
            updateStatement.setDouble(2, loan.getRemainingAmount());
            updateStatement.setInt(3, loan.getRemainingMonths());
            updateStatement.setString(4, loan.getStatus());
            updateStatement.setString(5, loan.getMemberId());
            
            
            int rowsAffected = updateStatement.executeUpdate();
            
            updateStatement.close();
            connection.close();
            
            return rowsAffected > 0; // Return true if rows were updated successfully
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurred
        }
    }
    
    public boolean recordInstallmentPayment(String memberId, String applicationNumber, double installmentAmount) {
        try {
            Connection connection = getConnection(); // Implement your database connection logic here
            
            // Insert the installment payment record into the loan_installments table
            String insertQuery = "INSERT INTO loan_repayments (memberId, application_number, amount_deposited, date) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, memberId);
            insertStatement.setString(2, applicationNumber);
            insertStatement.setDouble(3, installmentAmount);
            insertStatement.setDate(4, new java.sql.Date(System.currentTimeMillis())); // Set the payment date to the current date
            
            int rowsAffected = insertStatement.executeUpdate();
            
            insertStatement.close();
            connection.close();
            
            return rowsAffected > 0; // Return true if rows were inserted successfully
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurred
        }
    }
    
}



