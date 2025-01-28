package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValideUser(User user) {
        if(userRepository.existUser(user.getCpf())){
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
        return true;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findUserByCpf(String cpf) {
         return userRepository.findByCpf(cpf);

    }

}
