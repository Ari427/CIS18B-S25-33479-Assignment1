import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class BankAccount {
    private String accountHolderName;
    private int accountNumber;
    private double balance;
    private static int nextAccountNumber = 1001; // auto-incrementing account number

    public BankAccount(String name, double initialBalance) {
        this.accountHolderName = name;
        this.balance = initialBalance;
        this.accountNumber = nextAccountNumber++;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
        } else {
            balance += amount;
            System.out.println("Deposit successful! New balance: $" + balance);
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds!");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful! New balance: $" + balance);
        }
    }

    public void displayBalance() {
        System.out.println("Current balance: $" + balance);
    }
}

public class SimpleBankSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BankAccount> accounts = new ArrayList<>();
        BankAccount currentAccount = null;

        System.out.println("Welcome to Simple Bank System");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Switch Account");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); 
                continue;
            }

            switch (choice) {
                case 1: // Create Account
                    System.out.print("Enter account holder name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter initial deposit: ");
                    try {
                        double initialDeposit = scanner.nextDouble();
                        scanner.nextLine(); 
                        if (initialDeposit < 0) {
                            System.out.println("Initial deposit must be non-negative.");
                        } else {
                            BankAccount newAccount = new BankAccount(name, initialDeposit);
                            accounts.add(newAccount);
                            currentAccount = newAccount;
                            System.out.println(" Your account has been created!");
                            System.out.println("Account Number: " + newAccount.getAccountNumber());
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount, enter a number.");
                        scanner.nextLine(); 
                    }
                    break;

                case 2: // Deposit
                    if (currentAccount == null) {
                        System.out.println("No account selected, create or switch to an account.");
                        break;
                    }
                    System.out.print("Enter amount to deposit: ");
                    try {
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine();
                        currentAccount.deposit(depositAmount);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount.");
                        scanner.nextLine();
                    }
                    break;

                case 3: // Withdraw
                    if (currentAccount == null) {
                        System.out.println("No account selected, create or switch to an account.");
                        break;
                    }
                    System.out.print("Enter amount to withdraw: ");
                    try {
                        double withdrawAmount = scanner.nextDouble();
                        scanner.nextLine();
                        currentAccount.withdraw(withdrawAmount);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount.");
                        scanner.nextLine();
                    }
                    break;

                case 4: // Check Balance
                    if (currentAccount == null) {
                        System.out.println("No account selected, create or switch to an account.");
                    } else {
                        currentAccount.displayBalance();
                    }
                    break;

                case 5: // Switch Account
                    if (accounts.isEmpty()) {
                        System.out.println("No accounts available, create one first.");
                        break;
                    }

                    System.out.println("Available Accounts:");
                    for (BankAccount acc : accounts) {
                        System.out.println("- " + acc.getAccountHolderName() + " (Account #: " + acc.getAccountNumber() + ")");
                    }

                    System.out.print("Enter account number to switch to: ");
                    try {
                        int accNum = scanner.nextInt();
                        scanner.nextLine();
                        boolean found = false;
                        for (BankAccount acc : accounts) {
                            if (acc.getAccountNumber() == accNum) {
                                currentAccount = acc;
                                System.out.println("Successfully switched to account: " + acc.getAccountHolderName());
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Account not found.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.");
                        scanner.nextLine();
                    }
                    break;

                case 6: // Exit
                    running = false;
                    System.out.println("Thank you for using Simple Bank System, have a great day!");
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }

        scanner.close();
    }
}
