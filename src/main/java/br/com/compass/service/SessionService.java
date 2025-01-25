package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.model.Session;
import br.com.compass.model.User;
import br.com.compass.repository.AccountRepository;
import br.com.compass.repository.UserRepository;

import java.util.Optional;

public class SessionService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public SessionService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public boolean validateLogin(String cpf, String password){
        Optional<User> userOptional = userRepository.findByCpf(cpf);
        User user = userOptional.orElse(null);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            Optional<Account> AccOptional = accountRepository.findByUserId(user.getId());
            Account account = AccOptional.orElse(null);
            Session.setLoggedUser(user);
            if(account != null){
                Session.setUserAccount(account);
                return true;
            }
        }
        System.out.println("Invalid CPF or password, please try again!");
        return false;
    }
}
