package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.repository.UserRepository;

import java.util.Scanner;
import java.util.function.Predicate;


public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public <T> String validInput(Scanner scanner, String input,  String msg, Predicate<String> predicate){
        while(predicate.test(input)){
            System.out.print(msg);
            input = scanner.nextLine();
        }
        return input;
    }

    public boolean validPassword(String password) {
        if (password.length() < 8) return false;

        boolean haveUpper = password.matches(".*[A-Z].*");
        boolean haveLower = password.matches(".*[a-z].*");
        boolean haveNumber = password.matches(".*[0-9].*");
        boolean haveSpecial = password.matches(".*[!@#$%^&*()\\-+=<>?/\\[\\]{}.,;:].*");

        return haveUpper && haveLower && haveNumber && haveSpecial;
    }

    public String validatePassword(Scanner scanner, String password,  String msg){
        while(!validPassword(password)){
            System.out.print(msg);
            password = scanner.nextLine();
        }
        return password;
    }

    public boolean userAlreadyHaveAccount(String cpf){
        return userRepository.existUser(cpf);
    }

}
