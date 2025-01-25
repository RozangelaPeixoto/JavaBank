package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.model.Session;
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

    public Double convertValue(String value) {
        double amount;
        try{
            amount = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number");
        }
        return amount;
    }

    public void depositValue(String value) {
        double amount = convertValue(value);
        Account account = Session.getUserAccount();
        if(amount <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        accountRepository.deposit(amount, account.getId());
    }

    public Double checkBalance() {
        Account account = Session.getUserAccount();
        return accountRepository.balance(account.getId());
    }
}
