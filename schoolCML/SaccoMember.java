
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaccoMember {
    private String username;
    private String password;
    private String memberNumber;
    private String surname;
    private String firstName;
    private String gender;
    private String phoneNumber;
    private List<String> messages;
    private double monthlyExpectedDeposits = 100000;
    private double contribution;
    private boolean pendingLoans;
    private LoanApplication loanApplication;

    private static final String URL = "jdbc:mysql://localhost:3306/sacco";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Method to establish a database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public SaccoMember(String username,String memberNumber, String surname, String firstName, String gender) {
        this.username = username;      
        this.memberNumber = memberNumber;      
        this.surname = surname;      
        this.firstName = firstName;      
        this.gender = gender;      
    }

    public SaccoMember(String username, String password, String memberNumber, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.memberNumber = memberNumber;
        this.phoneNumber = phoneNumber;
       // this.pendingLoans = database.hasPendingLoanApplications(memberNumber);
                
    }  

    public boolean hasPendingLoanApplications(){
        return pendingLoans = hasPendingLoanApplications(memberNumber);
    }

    public boolean hasPendingLoanApplications(String memberNumber) {
        String query = "SELECT COUNT(*) FROM loan_application WHERE memberId = ? AND (status = 'pending' OR status = 'waiting approval')";
        
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

    public String getSurname(){
        return surname;
    }

    public String getFirstName(){
        return firstName;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<String> getMessages() {
        return messages;
    }

    public double getMonthlyContributionAmount(){
        return monthlyExpectedDeposits;
    }   
    
    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public double getContributions(){
        return contribution;
    }

    
      // Method to send a message to the member indicating insufficient funds.
public void sendInsufficientFundsMessage(String memberNumber, String applicationNumber) {
    String message;
    String username = getMemberUsername(memberNumber); // Fetch the username from the database
    String application_number = getLoanApplicationNumber(memberNumber);

    if (username != null) {
        message = "Dear " + username + ", we regret to inform you that there are insufficient funds to process your loan request at the moment. Please try again later.";
    } else {
        // In case the username is not found, use a generic message without the username
        message = "We regret to inform you that there are insufficient funds to process your loan request at the moment. Please try again later.";
    }

    try (Connection conn = getConnection()) {
        String insertQuery = "INSERT INTO member_messages (memberNo, application_number, message) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, memberNumber);
            pstmt.setString(2, application_number);
            pstmt.setString(3, message);
            pstmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Method to fetch the username from the database based on the memberId
private static String getMemberUsername(String memberId) {
    String username = null;
    try (Connection conn = getConnection()) {
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

private static String getLoanApplicationNumber(String memberId) {
    String application_number = null;
    try (Connection conn = getConnection()) {
        String selectQuery = "SELECT application_number FROM loan_application WHERE memberId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    application_number = rs.getString("application_number");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return application_number;
}

     
}



/*Handling loan applications involves a step-by-step process that includes receiving and processing loan requests, verifying the eligibility of applicants, evaluating the risk associated with the loan, and making decisions on loan approvals or rejections. Here's a general outline of how loan applications can be handled:

1. **Loan Request Submission**: Members of the Sacco submit loan requests using the provided command-line interface (CLI) or any other suitable method. The loan application typically includes details such as the requested loan amount, desired repayment period, and any other required information.

2. **Validation and Eligibility Check**: Upon receiving the loan request, the system verifies the eligibility of the applicant. This may involve checking if the member meets certain criteria, such as having a specific minimum contribution history, not having any overdue loans, or other relevant factors.

3. **Loan Application Processing**: The loan application is processed to determine its feasibility and risk level. The system may calculate the member's debt-to-income ratio, review their previous loan performance, and assess their ability to repay the loan.

4. **Loan Approval Decision**: Based on the processing and risk assessment, the system makes a decision on whether to approve or reject the loan application. If the application is rejected, the member is notified accordingly, and any funds that were reserved for the loan may be reallocated to other applicants.

5. **Loan Acceptance or Rejection by Member**: If the loan application is approved, the member is notified, and they have the option to accept or reject the loan offer. If the member accepts the loan, the system proceeds to disburse the funds and set up the repayment schedule.

6. **Loan Disbursement**: After loan acceptance, the funds are disbursed to the member's account, and the loan amount is deducted from the available funds of the Sacco.

7. **Loan Repayment and Monitoring**: The system monitors the repayment of the loan over the specified period. Members are notified of upcoming payments, and the system updates the loan status as payments are made.

8. **Handling Defaulters**: In case of loan default or late payments, the system may apply penalties or take appropriate actions based on the Sacco's policies. This may include notifying the member, suspending borrowing privileges, or initiating recovery processes.

9. **Loan Closure**: Once the loan is fully repaid, the system updates the loan status to "closed" or "repaid" and makes any necessary adjustments to the member's account.

10. **Reporting and Analytics**: The system may provide reports and analytics on loan performance, including loan portfolio quality, default rates, and other key performance indicators.

It's important to note that the specific implementation of loan application handling will depend on the requirements and policies of the particular Sacco. The steps and processes mentioned above can serve as a general guideline, but the actual implementation may vary based on the unique needs and regulations of the organization. Additionally, the use of secure authentication, data encryption, and other security measures is essential to protect sensitive financial information during the loan application process. */