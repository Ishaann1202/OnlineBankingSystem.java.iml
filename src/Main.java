import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        OnlineBankingSystem OnlineBankingSystemObject= new OnlineBankingSystem();
        OnlineBankingSystemObject.displayMessage();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "Ishu1234!");
            System.out.println(con);
        }
        catch(Exception e) {

        }
    }
}



class Account {
    private String accountNumber;
    private String accountType;
    private double balance;

    public Account(String accountNumber, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful. New balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Insufficient funds. Withdrawal failed.");
        }
    }
}

class User {
    private String username;
    private String password;
    private List<Account> accounts;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}

class Bank {
    private Map<String, User> users;

    public Bank() {
        this.users = new HashMap<>();
    }

    public void register(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
        } else {
            User user = new User(username, password);
            users.put(username, user);
            System.out.println("Registration successful.");
        }
    }

    public void login(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                System.out.println("Login successful. Welcome, " + username + "!");
                showMenu(user);
            } else {
                System.out.println("Invalid password. Login failed.");
            }
        } else {
            System.out.println("User not found. Login failed.");
        }
    }

    public void showMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n----- Menu -----");
            System.out.println("1. Create an account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View account balances");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAccount(user);
                    break;
                case 2:
                    deposit(user);
                    break;
                case 3:
                    withdraw(user);
                    break;
                case 4:
                    viewBalances(user);
                    break;
                case 0:
                    System.out.println("Logout successful. Goodbye, " + user.getUsername() + "!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public void createAccount(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter account type: ");
        String accountType = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        Account account = new Account(accountNumber, accountType, balance);
        user.addAccount(account);
        System.out.println("Account created successfully.");
    }

    public void deposit(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        for (Account account : user.getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.deposit(amount);
                return;
            }
        }
        System.out.println("Account not found. Deposit failed.");
    }

    public void withdraw(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        for (Account account : user.getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.withdraw(amount);
                return;
            }
        }
        System.out.println("Account not found. Withdrawal failed.");
    }

    public void viewBalances(User user) {
        System.out.println("\nAccount Balances:");
        for (Account account : user.getAccounts()) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("--------------------");
        }
    }
}

