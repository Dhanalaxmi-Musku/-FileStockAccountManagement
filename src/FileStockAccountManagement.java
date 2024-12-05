import java.util.Scanner;

public class FileStockAccountManagement {

	public static void main(String[] args) {
		 Scanner scanner = new Scanner(System.in);
	        
	        System.out.print("Enter filename for account: ");
	        String filename = scanner.nextLine();
	        
	        StockAccount account = new StockAccount(filename);

	        while (true) {
	            System.out.println("\n--- Stock Account Menu ---");
	            System.out.println("1. Buy Shares");
	            System.out.println("2. Sell Shares");
	            System.out.println("3. Print Report");
	            System.out.println("4. Save Account");
	            System.out.println("5. Exit");
	            System.out.print("Enter your choice: ");

	            int choice = scanner.nextInt();
	            scanner.nextLine();

	            switch (choice) {
	                case 1:
	                    System.out.print("Enter stock symbol: ");
	                    String buySymbol = scanner.nextLine();
	                    System.out.print("Enter number of shares to buy: ");
	                    int buyAmount = scanner.nextInt();
	                    account.buy(buyAmount, buySymbol);
	                    break;
	                case 2:
	                    System.out.print("Enter stock symbol: ");
	                    String sellSymbol = scanner.nextLine();
	                    System.out.print("Enter number of shares to sell: ");
	                    int sellAmount = scanner.nextInt();
	                    account.sell(sellAmount, sellSymbol);
	                    break;
	                case 3:
	                    account.printReport();
	                    break;
	                case 4:
	                    account.save(filename);
	                    break;
	                case 5:
	                    System.out.println("Exiting Stock Account Management.");
	                    scanner.close();
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }

	}

}
