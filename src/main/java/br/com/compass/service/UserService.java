package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(String nome, String email) {
        User user = new User();
        user.setName(nome);
        user.setEmail(email);
        return userRepository.save(user);
    }
}
