import java.time.LocalDate;

public class LoanPayment {
    private LocalDate date;
    private double amount;
    private int paymentPeriod;
    private int clearedMonths;
    

    public LoanPayment(int paymentPeriod, int clearedMonths) {
        this.paymentPeriod = paymentPeriod;
        this.clearedMonths = clearedMonths;
    }

    public LoanPayment(LocalDate date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public LoanPayment(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public int getPaymentPeriod(){
        return paymentPeriod;
    }

    public int getClearedMonths(){
        return clearedMonths;
    }

}
