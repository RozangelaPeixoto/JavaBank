package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.repository.AccountRepository;

import java.util.Optional;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountByUser(Integer id){
        Optional<Account> optionalAccount = accountRepository.findByUserId(id);
        return optionalAccount.orElse(null);

    }
}
