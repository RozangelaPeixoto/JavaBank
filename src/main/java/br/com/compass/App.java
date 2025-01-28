package br.com.compass;

import br.com.compass.model.*;
import br.com.compass.model.enums.AccountType;
import br.com.compass.service.AccountService;
import br.com.compass.service.UserService;
import br.com.compass.util.Connection;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Predicate;

public class App {

    static EntityManager entityManager = Connection.getEntityManager();
    static UserService userService = Connection.getUserService();
    static AccountService accountService = Connection.getAccountService();
    static Account loggedAccount = null;

    static DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //Connection.create();
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

        Predicate<String> isStringValid = pName -> pName == null || pName.isBlank();
        Predicate<String> isCpfValid = pCpf -> pCpf == null || !pCpf.matches("^\\d{11}$");
        Predicate<String> isPhoneValid = pPhone -> pPhone == null || !pPhone.matches("^\\d+$");
        Predicate<String> isEmailValid = pEmail -> pEmail == null || !pEmail.contains("@");
        System.out.println();
        System.out.println("======== New Account ========");
        System.out.print("Name: ");
        scanner.nextLine();
        String name = userService.validInput(scanner, scanner.nextLine(), "Name is required.\nName: ", isStringValid);
        System.out.print("CPF: ");
        String cpf = userService.validInput(scanner, scanner.nextLine(), "Invalid CPF.\nCPF: ", isCpfValid);

        if(userService.userAlreadyHaveAccount(cpf)){
            System.out.println("User already has an account!");
            System.out.println();
            return;
        }

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
        String phone = userService.validInput(scanner, scanner.nextLine(), "Invalid phone number.\nPhone: ", isPhoneValid);

        System.out.print("Email: ");
        String email = userService.validInput(scanner, scanner.nextLine(), "Invalid email.\nEmail: ", isEmailValid);

        System.out.print("""
                Password Requirements:
                8 characters
                1 uppercase letter
                1 lowercase letter
                1 number
                1 special character
                """);
        System.out.print("Password: ");
        String password = userService.validatePassword(scanner, scanner.nextLine(), "Invalid password.\nPassword: ");
        System.out.print("""
                Select account type:
                1. Business Account
                2. Checking Account
                3. Salary Account
                4. Savings Account
                """);
        System.out.print("Enter the number: ");
        String stringType = scanner.nextLine();
        AccountType type = switch (stringType) {
            case "1" -> AccountType.BUSINESS;
            case "3" -> AccountType.SALARY;
            case "4" -> AccountType.SAVINGS;
            default -> AccountType.CHECKING;
        };

        Account newAccount = new Account();
        newAccount.setPassword(password);
        newAccount.setType(type);
        User newUser = new User(null, name, cpf, birthDate, phone, email, newAccount);
        newAccount.setHolder(newUser);
        userService.saveUser(newUser);
        System.out.println("Your account has been created, use your CPF and password to log in.");
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

        if(accountService.validLogin(cpf,password)){
            loggedAccount = accountService.getAccount(cpf);
            if(loggedAccount == null){
                System.out.println("Access denied. Try again!");
            }else{
                System.out.println("=============================");
                System.out.println();
                System.out.println("Welcome, " + loggedAccount.getHolder().getName());
                System.out.println("Account Number: " + loggedAccount.getAccNumber());
                System.out.println("Account Type: " + loggedAccount.getType());
                bankMenu(scanner);
            }
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
            accountService.depositValue(value, loggedAccount.getId());
            System.out.println("Balance: R$ " + loggedAccount.getBalance());
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void withdraw(Scanner scanner) {

        System.out.println();
        System.out.println("========== Withdraw =========");
        scanner.nextLine();
        System.out.println("Balance: R$ " + loggedAccount.getBalance());
        if(loggedAccount.getBalance() == 0){
            System.out.println("Insufficient balance.");
            System.out.println();
            return;
        }
        System.out.print("Enter the amount to withdraw: ");
        String value = scanner.nextLine();
        try{
            accountService.withdrawValue(value, loggedAccount.getId());
            System.out.println("Balance: R$ " + loggedAccount.getBalance());
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void showBalance() {
        System.out.println();
        System.out.println("========== Balance ==========");
        System.out.println("Balance: R$ " + accountService.checkBalance(loggedAccount.getId()));
        System.out.println();
    }

    private static void transfer(Scanner scanner) {

        System.out.println();
        System.out.println("========== Transfer =========");
        scanner.nextLine();
        System.out.println("Balance: R$ " + loggedAccount.getBalance());
        System.out.print("Enter the amount to transfer: ");
        String value = scanner.nextLine();
        System.out.print("Enter the account number to transfer: ");
        String accNumber = scanner.nextLine();
        try{
            accountService.transferValue(value, accNumber, loggedAccount.getId());
            System.out.println("Balance: R$ " + loggedAccount.getBalance());
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void bankStatement() {

        System.out.println();
        System.out.println("======= Bank Statement ======");
        for(Transaction transaction : loggedAccount.getTransactions()){
            System.out.println(transaction);
        }
        System.out.println();
    }
}
