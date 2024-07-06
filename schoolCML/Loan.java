public class Loan {
    private String memberId;
    private int clearedMonths;
    private int remainingMonths;
    private double expectedInstallmentAmount;
    private double remainingAmount;
    private String applicationNumber;
    private String status;

    public Loan(){
       
    }

    public int getClearedMonths(){
        return clearedMonths;
    }

    public void setClearedMonths(int clearedMonths) {
        this.clearedMonths = clearedMonths;
    }

    public int getRemainingMonths(){
        return remainingMonths;
    }

    public void setRemainingMonths(int remainingMonths) {
        this.remainingMonths = remainingMonths;
    }

    public double getExpectedInstallmentAmount(){
        return expectedInstallmentAmount;
    }

    public void setExpectedInstallmentAmount(double expectedInstallmentAmount){
        this.expectedInstallmentAmount = expectedInstallmentAmount;
    }

    public double getRemainingAmount(){
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount){
        this.remainingAmount = remainingAmount;
    }

    public String getMemberId(){
        return memberId;
    }

    public void setMemberId(String memberId){
        this.memberId = memberId;
    }

    public String getApplicationNumber(){
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber){
        this.applicationNumber = applicationNumber;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
