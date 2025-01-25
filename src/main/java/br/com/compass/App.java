package br.com.compass;

import br.com.compass.model.*;
import br.com.compass.repository.AccountRepository;
import br.com.compass.repository.UserRepository;
import br.com.compass.service.AccountService;
import br.com.compass.service.SessionService;
import br.com.compass.service.UserService;
import br.com.compass.util.Conn;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class App {

    static EntityManager entityManager = Conn.getEntityManager();
    static UserRepository userRepository = new UserRepository(entityManager);
    static AccountRepository accountRepository = new AccountRepository(entityManager);
    static UserService userService = new UserService(userRepository);
    static AccountService accountService = new AccountService(accountRepository);
    static SessionService sessionService = new SessionService(userRepository, accountRepository);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
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
                    // ToDo...
                    System.out.println("Deposit.");
                    break;
                case 2:
                    // ToDo...
                    System.out.println("Withdraw.");
                    break;
                case 3:
                    // ToDo...
                    System.out.println("Check Balance.");
                    break;
                case 4:
                    // ToDo...
                    System.out.println("Transfer.");
                    break;
                case 5:
                    // ToDo...
                    System.out.println("Bank Statement.");
                    break;
                case 0:
                    // ToDo...
                    System.out.println("Exiting...");
                    running = false;
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void openAccount(Scanner scanner) {

        System.out.println();
        System.out.println("Please respond the following questions to open your account:");
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
                birthDate = LocalDate.parse(date, formatter);
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

        User newUser = new User(null, name, cpf, birthDate, phone, email, password);
        User user = userService.saveUser(newUser);

        System.out.print("""
                Select account type:
                1 - Business Account
                2 - Checking Account
                3 - Salary Account
                4 - Savings Account
                """);
        System.out.print("Enter the number: ");
        String stringType = scanner.nextLine();

        Account newAccount = switch (stringType) {
            case "1" -> new BusinessAccount(null, user);
            case "2" -> new CheckingAccount(null, user);
            case "3" -> new SalaryAccount(null, user);
            case "4" -> new SavingsAccount(null,user);
            default -> throw new IllegalArgumentException("Invalid type!");
        };
        Account account = accountService.saveAccount(newAccount);

        System.out.println("Your account has been opened, use your CPF and password to log in.");
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
            bankMenu(scanner);
        }else{
            System.out.println();
            mainMenu(scanner);
        }
    }
    
}
