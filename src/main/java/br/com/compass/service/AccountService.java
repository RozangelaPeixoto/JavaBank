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
            return null;
        }
        return amount;
    }

    public void depositValue(String value) {
        Double amount = convertValue(value);
        if(amount == null){
            throw new IllegalArgumentException("Invalid number");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        Account account = Session.getUserAccount();
        accountRepository.deposit(amount, account.getId());
    }

    public void withdrawValue(String value){
        Double amount = convertValue(value);
        if(amount == null){
            throw new IllegalArgumentException("Invalid number");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("Withdraw amount must be greater than zero.");
        }
        Account account = Session.getUserAccount();
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance to make withdrawal.");
        }
        accountRepository.withdraw(amount, account.getId());
    }

    public Double checkBalance() {
        Account account = Session.getUserAccount();
        return accountRepository.balance(account.getId());
    }
}
