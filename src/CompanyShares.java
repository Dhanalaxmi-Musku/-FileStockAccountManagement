import java.time.LocalDateTime;

public class CompanyShares {
	private String symbol;
    private int numberOfShares;
    private LocalDateTime transactionTime;
    private double sharePrice;

    public CompanyShares(String symbol, int numberOfShares, double sharePrice) {
        this.symbol = symbol;
        this.numberOfShares = numberOfShares;
        this.sharePrice = sharePrice;
        this.transactionTime = LocalDateTime.now();
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public double getSharePrice() {
        return sharePrice;
    }

    public double getTotalValue() {
        return numberOfShares * sharePrice;
    }

}
