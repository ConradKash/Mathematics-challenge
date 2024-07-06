import java.time.LocalDate;

public class Contribution {
     private LocalDate date;
    private double amount;

    public Contribution(LocalDate date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
