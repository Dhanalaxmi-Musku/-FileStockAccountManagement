import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class StockAccount {
	private List<CompanyShares> companyShares;
    private double balance;

    public StockAccount(String filename) {
        this.companyShares = new ArrayList<>();
        loadFromFile(filename);
    }

    public double valueOf() {
        return companyShares.stream()
                .mapToDouble(CompanyShares::getTotalValue)
                .sum();
    }

    public void buy(int amount, String symbol) {
        double sharePrice = getSharePrice(symbol);
        double totalCost = amount * sharePrice;

        if (totalCost <= balance) {
            Optional<CompanyShares> existingShare = companyShares.stream()
                    .filter(share -> share.getSymbol().equals(symbol))
                    .findFirst();

            if (existingShare.isPresent()) {
                CompanyShares share = existingShare.get();
                share.setNumberOfShares(share.getNumberOfShares() + amount);
            } else {
                companyShares.add(new CompanyShares(symbol, amount, sharePrice));
            }

            balance -= totalCost;
            System.out.println("Bought " + amount + " shares of " + symbol);
        } else {
            System.out.println("Insufficient balance to buy shares");
        }
    }

    public void sell(int amount, String symbol) {
        Optional<CompanyShares> existingShare = companyShares.stream()
                .filter(share -> share.getSymbol().equals(symbol))
                .findFirst();

        if (existingShare.isPresent()) {
            CompanyShares share = existingShare.get();
            if (share.getNumberOfShares() >= amount) {
                double sharePrice = share.getSharePrice();
                double totalSale = amount * sharePrice;

                share.setNumberOfShares(share.getNumberOfShares() - amount);
                balance += totalSale;

                if (share.getNumberOfShares() == 0) {
                    companyShares.remove(share);
                }

                System.out.println("Sold " + amount + " shares of " + symbol);
            } else {
                System.out.println("Insufficient shares to sell");
            }
        } else {
            System.out.println("No shares of " + symbol + " found");
        }
    }

    public void save(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(balance);
            for (CompanyShares share : companyShares) {
                writer.println(share.getSymbol() + "," + 
                               share.getNumberOfShares() + "," + 
                               share.getSharePrice());
            }
            System.out.println("Account saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving account: " + e.getMessage());
        }
    }

    private void loadFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            balance = scanner.hasNextDouble() ? scanner.nextDouble() : 0.0;
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String symbol = parts[0];
                    int numberOfShares = Integer.parseInt(parts[1]);
                    double sharePrice = Double.parseDouble(parts[2]);
                    
                    companyShares.add(new CompanyShares(symbol, numberOfShares, sharePrice));
                }
            }
            System.out.println("Account loaded from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("No existing account file found. Creating new account.");
        }
    }

    public void printReport() {
        System.out.println("\n--- Stock Portfolio Report ---");
        System.out.println("Current Balance: " + String.format("%.2f", balance));
        System.out.println("\nCompany Shares:");
        System.out.println("Symbol\tShares\tPrice/Share\tTotal Value");
        System.out.println("-------------------------------------------");

        for (CompanyShares share : companyShares) {
            System.out.println(share.getSymbol() + "\t" + 
                               share.getNumberOfShares() + "\t" + 
                               String.format("%.2f", share.getSharePrice()) + "\t\t" + 
                               String.format("%.2f", share.getTotalValue()));
        }

        System.out.println("\nTotal Portfolio Value: " + String.format("%.2f", valueOf()));
    }

    private double getSharePrice(String symbol) {
        return 100.0 + new Random().nextDouble() * 50.0;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
