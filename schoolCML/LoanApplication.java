import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class LoanApplication {
    private String applicationNumber;
    private String memberId;
    private String surname;
    private String firstName;
    private double requestedAmount;
    private int paymentPeriod;
    private String status;
    private double loanProgress;
    private Date applicationDate;
    private double loanAmount;
    private double expected_installments;
    private double expectedLoanRepaymentAmount;
    private int paymentPeriodInMonths;
    private int cleared_months;
    private LocalDate start_date;

    private static final String URL = "jdbc:mysql://localhost:3306/sacco";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Method to establish a database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

   
    public LoanApplication(String applicationNumber, double requestedAmount, int paymentPeriod, String status, Date applicationDate, String memberId, String surname, String firstName) {
        this.applicationNumber = applicationNumber;
        this.requestedAmount = requestedAmount;
        this.paymentPeriod = paymentPeriod;
        this.status = status;
        this.applicationDate = applicationDate;
        this.memberId = memberId;
        this.surname = surname;
        this.firstName = firstName;
    }
    
    public LoanApplication(String applicationNumber, double loanAmount, double expected_installments, double expectedLoanRepaymentAmount, int paymentPeriodInMonths, int cleared_months, LocalDate start_date, String status){
        this.applicationNumber = applicationNumber;
        this.loanAmount = loanAmount;
        this.expected_installments = expected_installments;
        this.expectedLoanRepaymentAmount = expectedLoanRepaymentAmount;
        this.paymentPeriodInMonths = paymentPeriodInMonths;
        this.cleared_months = cleared_months;
        this.start_date = start_date;
        this.status = status;

    }

    public String getSurname(){
        return surname;
    }

    public String getFirstName(){
        return firstName;
    }


    public String getApplicationNumber() {
        return applicationNumber;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return applicationDate;
    }

    public double getLoanAmount(){
        return loanAmount;
    }

    public double getExpectedInstallments(){
        return expected_installments;
    }

    public double getExpectedLoanRepaymentAmount(){
        return expectedLoanRepaymentAmount;
    }

    public int getPaymentPeriodInMonths(){
        return paymentPeriodInMonths;
    }

    public int getClearedMonths(){
        return cleared_months;

    }

    public LocalDate getStartDate(){
        return start_date;
    }

    public double getLoanProgress(){
        return loanProgress;
    }

    public void setLoanProgress(double loanProgress){
        this.loanProgress = loanProgress;
    }

    public String getmemberId() {
        return memberId;
    }
    public void setMemberNo(String memberId) {
        this.memberId = memberId;
    }
    
    public String getUsername() {
       
        String username = getUsernameBymemberId(memberId); 
        return username;
    }

    public String getUsernameBymemberId(String memberId) {
        String username = ""; 
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT username FROM members WHERE memberId = ?");
        ) {
            statement.setString(1, memberId);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return username;
    }

    public double getContributionsForMember(String memberId) {
    double contributions = 0.0;
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement("SELECT SUM(amount) AS total_Contribution FROM deposits WHERE memberId = ?")) {

        stmt.setString(1, memberId);
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
}
