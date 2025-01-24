package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }
        String regex = "^\\d{11}$";
        if (user.getCpf() == null || !user.getCpf().matches(regex)) {
            throw new IllegalArgumentException("Invalid CPF.");
        }
        if (user.getPhone() == null || user.getPhone().matches(regex)) {
            throw new IllegalArgumentException("Phone is required.");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
        return userRepository.save(user);
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User findUserByCpf(String cpf) {
        Optional<User> userOptional = userRepository.findByCpf(cpf);

        return userOptional.orElse(null);
    }

    public boolean validateLogin(String cpf, String password){
        User user = findUserByCpf(cpf);

        if(user == null){
            System.out.println("This CPF is not in our database.");
            return false;
        }
        if(!password.equals(user.getPassword())){
            System.out.println("Invalid password.");
            return false;
        }
        System.out.println();
        System.out.println("Welcome " + user.getName());
        return true;
    }
}
