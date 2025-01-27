package br.com.compass;

import br.com.compass.model.*;
import br.com.compass.service.AccountService;
import br.com.compass.service.SessionService;
import br.com.compass.service.TransactionService;
import br.com.compass.service.UserService;
import br.com.compass.util.Connection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class App {

    static UserService userService = Connection.getUserService();
    static AccountService accountService = Connection.getAccountService();
    static TransactionService TransactionService = Connection.getTransactionService();
    static SessionService sessionService = Connection.getSessionService();

    static DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        mainMenu(scanner);
        
        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    userLogin(scanner);
                    return;
                case 2:
                    openAccount(scanner);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    deposit(scanner);
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    showBalance();
                    break;
                case 4:
                    transfer(scanner);
                    break;
                case 5:
                    bankStatement();
                    break;
                case 0:
                    Connection.close();
                    running = false;
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void openAccount(Scanner scanner) {

        System.out.println();
        System.out.println("======== New Account ========");
        System.out.print("Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        LocalDate birthDate = null;
        boolean formatValid = false;
        while(!formatValid) {
            try {
                System.out.print("Birth Date (dd/mm/yyyy): ");
                String date = scanner.nextLine();
                birthDate = LocalDate.parse(date, formatDate);
                formatValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Incorrect format date. Please try again!");
            }
        }

        System.out.print("Phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Account newAccount = null;
        while(newAccount == null) {
            System.out.print("""
                    Select account type:
                    1. Business Account
                    2. Checking Account
                    3. Salary Account
                    4. Savings Account
                    """);
            System.out.print("Enter the number: ");
            String stringType = scanner.nextLine();
            newAccount = switch (stringType) {
                case "1" -> new BusinessAccount();
                case "2" -> new CheckingAccount();
                case "3" -> new SalaryAccount();
                case "4" -> new SavingsAccount();
                default -> {
                    System.out.println("Invalid option. Please try again!");
                    System.out.println();
                    yield null;
                }
            };
        }

        User newUser = new User(null, name, cpf, birthDate, phone, email, password, newAccount);
        if(userService.isvalideUser(newUser)){
            newAccount.setHolder(newUser);
            userService.saveUser(newUser);
            System.out.println("Your account has been created, use your CPF and password to log in.");
        }
        System.out.println();
    }

    private static void userLogin(Scanner scanner) {

        System.out.println();
        System.out.println("========= Bank Login ========");
        System.out.print("Enter your cpf: ");
        scanner.nextLine();
        String cpf = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if(sessionService.validateLogin(cpf,password)){
            System.out.println();
            System.out.println("Welcome, " + Session.getLoggedUser().getName());
            bankMenu(scanner);
        }else{
            System.out.println();
            mainMenu(scanner);
        }
    }

    private static void deposit(Scanner scanner) {

        System.out.println();
        System.out.println("========== Deposit ==========");
        scanner.nextLine();
        System.out.print("Enter the amount to deposit: ");
        String value = scanner.nextLine();

        try{
            accountService.depositValue(value);
            System.out.println("Balance: " + accountService.checkBalance());
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void withdraw(Scanner scanner) {

        System.out.println();
        System.out.println("========== Withdraw =========");
        scanner.nextLine();
        System.out.println("Balance: " + accountService.checkBalance());
        System.out.print("Enter the amount to withdraw: ");
        String value = scanner.nextLine();
        try{
            accountService.withdrawValue(value);
            System.out.println("Balance: " + accountService.checkBalance());
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void showBalance() {
        System.out.println();
        System.out.println("========== Balance ==========");
        System.out.println("Balance: " + accountService.checkBalance());
        System.out.println();
    }

    private static void transfer(Scanner scanner) {

        System.out.println();
        System.out.println("========== Transfer =========");
        scanner.nextLine();
        System.out.println("Balance: " + accountService.checkBalance());
        System.out.print("Enter the amount to transfer: ");
        String value = scanner.nextLine();
        System.out.print("Enter the account number to transfer: ");
        String accNumber = scanner.nextLine();
        try{
            accountService.transferValue(value, accNumber);
            System.out.println("Balance: " + accountService.checkBalance());
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void bankStatement() {

        System.out.println();
        System.out.println("======= Bank Statement ======");
        List<Transaction> transactionList = TransactionService.getBankStatement();
        for(Transaction transaction : transactionList){
            System.out.println(transaction);
        }
        System.out.println("Balance: " + Session.getUserAccount().getBalance());
        System.out.println();
    }
}
