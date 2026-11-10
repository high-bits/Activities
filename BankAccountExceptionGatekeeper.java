import java.util.InputMismatchException;
import java.util.Scanner;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid Amount! Deposit must be greater than 0.");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid Amount! Withdrawal must be greater than 0.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(String.format(
                "Insufficient Funds! Required: $%.2f, Available: $%.2f", amount, balance
            ));
        }
        balance -= amount;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Setting up initial mock account matching the expected output schema
        BankAccount account = new BankAccount("ACC-1001", "Alex Cruz", 500.00);
        
        System.out.println("=== BANK ACCOUNT EXCEPTION GATEKEEPER ===");
        System.out.printf("Account Created: %s (Holder: %s, Balance: $%.2f)\n", 
                account.getAccountNumber(), account.getAccountHolder(), account.getBalance());

        try {
          
            System.out.print("Enter withdrawal amount: ");
            double withdrawAmount = scanner.nextDouble();
            account.withdraw(withdrawAmount);
            System.out.printf("Withdrawal successful! New balance: $%.2f\n", account.getBalance());

        } catch (InputMismatchException e) {
            System.out.println("[EXCEPTION CAUGHT]: Invalid input type! Please enter numerical values.");
            scanner.nextLine(); // Clear scanner buffer
        } catch (InsufficientFundsException e) {
            System.out.println("[EXCEPTION CAUGHT]: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[EXCEPTION CAUGHT]: " + e.getMessage());
        } finally {
            System.out.println("[SESSION COMPLETE]: Transaction log updated.");
            System.out.printf("Current Balance: $%.2f\n", account.getBalance());
            scanner.close();
        }
    }
}