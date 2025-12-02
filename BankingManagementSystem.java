import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Transaction class to represent individual transactions
class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%-12s | $%-10.2f | Balance: $%-10.2f | %s",
                type, amount, balanceAfter, timestamp.format(formatter));
    }
}

// Abstract Account class demonstrating OOP inheritance
abstract class Account {
    protected String accountNumber;
    protected String accountHolder;
    protected String hashedPassword;
    protected double balance;
    protected List<Transaction> transactions;

    public Account(String accountNumber, String accountHolder, String password) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.hashedPassword = hashPassword(password);
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    // Password hashing using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    public boolean verifyPassword(String password) {
        return hashPassword(password).equals(hashedPassword);
    }

    // Encapsulation - getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    // Abstract method for polymorphism
    public abstract String getAccountType();

    // Deposit method with validation
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid amount. Deposit must be positive.");
            return false;
        }
        balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount, balance));
        System.out.println("✅ Deposited $" + amount + " successfully!");
        return true;
    }

    // Withdraw method with validation
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid amount. Withdrawal must be positive.");
            return false;
        }
        if (amount > balance) {
            System.out.println("❌ Insufficient funds!");
            return false;
        }
        balance -= amount;
        transactions.add(new Transaction("WITHDRAW", amount, balance));
        System.out.println("✅ Withdrawn $" + amount + " successfully!");
        return true;
    }

    // Mini statement - last 5 transactions
    public void printMiniStatement() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                    MINI STATEMENT");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Account: " + accountNumber + " | Holder: " + accountHolder);
        System.out.println("Type: " + getAccountType());
        System.out.println("Current Balance: $" + String.format("%.2f", balance));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Last " + Math.min(5, transactions.size()) + " Transactions:");
            int start = Math.max(0, transactions.size() - 5);
            for (int i = transactions.size() - 1; i >= start; i--) {
                System.out.println(transactions.get(i));
            }
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }

    // Full transaction history
    public void printTransactionHistory() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                 TRANSACTION HISTORY");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Account: " + accountNumber + " | Holder: " + accountHolder);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Total Transactions: " + transactions.size());
            for (int i = transactions.size() - 1; i >= 0; i--) {
                System.out.println((transactions.size() - i) + ". " + transactions.get(i));
            }
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }
}

// Savings Account - concrete implementation
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, String password) {
        super(accountNumber, accountHolder, password);
        this.interestRate = 3.5; // 3.5% interest
    }

    @Override
    public String getAccountType() {
        return "SAVINGS ACCOUNT";
    }

    public void applyInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
        transactions.add(new Transaction("INTEREST", interest, balance));
        System.out.println("✅ Interest of $" + String.format("%.2f", interest) + " applied!");
    }
}

// Current Account - concrete implementation
class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolder, String password) {
        super(accountNumber, accountHolder, password);
        this.overdraftLimit = 1000.0;
    }

    @Override
    public String getAccountType() {
        return "CURRENT ACCOUNT";
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid amount. Withdrawal must be positive.");
            return false;
        }
        if (amount > balance + overdraftLimit) {
            System.out.println("❌ Exceeds overdraft limit!");
            return false;
        }
        balance -= amount;
        transactions.add(new Transaction("WITHDRAW", amount, balance));
        System.out.println("✅ Withdrawn $" + amount + " successfully!");
        return true;
    }
}

// Bank class - manages all accounts
class Bank {
    private Map<String, Account> accounts;
    private int accountCounter;

    public Bank() {
        this.accounts = new HashMap<>();
        this.accountCounter = 1000;
    }

    // Create new account
    public String createAccount(String name, String password, String accountType) {
        String accountNumber = "ACC" + (++accountCounter);
        Account account;

        if (accountType.equalsIgnoreCase("savings")) {
            account = new SavingsAccount(accountNumber, name, password);
        } else {
            account = new CurrentAccount(accountNumber, name, password);
        }

        accounts.put(accountNumber, account);
        System.out.println("\n✅ Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + name);
        System.out.println("Account Type: " + account.getAccountType());
        return accountNumber;
    }

    // Login to account
    public Account login(String accountNumber, String password) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("❌ Account not found!");
            return null;
        }
        if (!account.verifyPassword(password)) {
            System.out.println("❌ Invalid password!");
            return null;
        }
        System.out.println("✅ Login successful! Welcome, " + account.getAccountHolder());
        return account;
    }

    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts in the system.");
            return;
        }
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              ALL ACCOUNTS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Account acc : accounts.values()) {
            System.out.println("Account: " + acc.getAccountNumber() +
                    " | Holder: " + acc.getAccountHolder() +
                    " | Type: " + acc.getAccountType() +
                    " | Balance: $" + String.format("%.2f", acc.getBalance()));
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }
}

// Main class with interactive menu
public class BankingManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║       BANKING MANAGEMENT SYSTEM        ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");

        while (true) {
            System.out.println("\n┌─────────────────────────────┐");
            System.out.println("│        MAIN MENU            │");
            System.out.println("├─────────────────────────────┤");
            System.out.println("│ 1. Create Account           │");
            System.out.println("│ 2. Login to Account         │");
            System.out.println("│ 3. Display All Accounts     │");
            System.out.println("│ 4. Exit                     │");
            System.out.println("└─────────────────────────────┘");
            System.out.print("Choose option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createAccountMenu();
                    break;
                case 2:
                    loginMenu();
                    break;
                case 3:
                    bank.displayAllAccounts();
                    break;
                case 4:
                    System.out.println("\n👋 Thank you for using Banking Management System!");
                    System.exit(0);
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    private static void createAccountMenu() {
        System.out.println("\n--- CREATE NEW ACCOUNT ---");
        System.out.print("Enter your name: ");
        scanner.nextLine(); // Clear buffer
        String name = scanner.nextLine();

        System.out.print("Create password: ");
        String password = scanner.nextLine();

        System.out.print("Account type (1-Savings / 2-Current): ");
        int type = getIntInput();

        String accountType = (type == 1) ? "savings" : "current";
        bank.createAccount(name, password, accountType);
    }

    private static void loginMenu() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Account Number: ");
        scanner.nextLine(); // Clear buffer
        String accountNumber = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Account account = bank.login(accountNumber, password);
        if (account != null) {
            accountOperationsMenu(account);
        }
    }

    private static void accountOperationsMenu(Account account) {
        while (true) {
            System.out.println("\n┌─────────────────────────────┐");
            System.out.println("│     ACCOUNT OPERATIONS      │");
            System.out.println("├─────────────────────────────┤");
            System.out.println("│ 1. Deposit                  │");
            System.out.println("│ 2. Withdraw                 │");
            System.out.println("│ 3. Check Balance            │");
            System.out.println("│ 4. Mini Statement           │");
            System.out.println("│ 5. Transaction History      │");
            if (account instanceof SavingsAccount) {
                System.out.println("│ 6. Apply Interest           │");
            }
            System.out.println("│ 0. Logout                   │");
            System.out.println("└─────────────────────────────┘");
            System.out.print("Choose option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = getDoubleInput();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawAmount = getDoubleInput();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.println("\n💰 Current Balance: $" +
                            String.format("%.2f", account.getBalance()));
                    break;
                case 4:
                    account.printMiniStatement();
                    break;
                case 5:
                    account.printTransactionHistory();
                    break;
                case 6:
                    if (account instanceof SavingsAccount) {
                        ((SavingsAccount) account).applyInterest();
                    } else {
                        System.out.println("❌ Invalid option.");
                    }
                    break;
                case 0:
                    System.out.println("👋 Logged out successfully!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}