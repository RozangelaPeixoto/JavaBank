package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isvalideUser(User user) {
        if(findUserByCpf(user.getCpf()) != null){
            System.out.println("User already has an account.\n");
            return false;
        }
        if (user.getName() == null || user.getName().isBlank()) {
            System.out.println("Name is required.\n");
            return false;
        }
        String regexCPF = "^\\d{11}$";
        if (user.getCpf() == null || !user.getCpf().matches(regexCPF)) {
            System.out.println("Invalid CPF.\n");
            return false;
        }
        String regexPhone = "^\\d+$";
        if (user.getPhone() == null || !user.getPhone().matches(regexPhone)) {
            System.out.println("Phone is required.\n");
            return false;
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            System.out.println("Invalid email.\n");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            System.out.println("Password is required.\n");
            return false;
        }
        return true;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findUserByCpf(String cpf) {
        Optional<User> userOptional = userRepository.findByCpf(cpf);

        return userOptional.orElse(null);
    }

}
