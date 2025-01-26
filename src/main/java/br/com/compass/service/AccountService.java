package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.model.Session;
import br.com.compass.repository.AccountRepository;

import java.text.DecimalFormat;
import java.util.Optional;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountByAccountNumber(String accNumber) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accNumber);
        return accountOptional.orElse(null);
    }

    public Double convertValue(String value) {
        double amount;
        try{
            amount = Math.round(Double.parseDouble(value) * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            return null;
        }
        return amount;
    }

    public void depositValue(String value) {
        Double amount = convertValue(value);
        if(amount == null){
            throw new IllegalArgumentException("Invalid number.");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        Account account = Session.getUserAccount();
        accountRepository.deposit(amount, account.getId());
    }

    public void withdrawValue(String value){
        Double amount = convertValue(value);
        if(amount == null){
            throw new IllegalArgumentException("Invalid number.");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        Account account = Session.getUserAccount();
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        accountRepository.withdraw(amount, account.getId());
    }

    public Double checkBalance() {
        Account account = Session.getUserAccount();
        return accountRepository.balance(account.getId());
    }

    public void transferValue(String value, String accNumber){
        Double amount = convertValue(value);
        if(amount == null){
            throw new IllegalArgumentException("Invalid number.");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        Account account = Session.getUserAccount();
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        if(accNumber.equals(account.getAccNumber())){
            throw new IllegalArgumentException("Target account and source account cannot be the same.");
        }
        Account targetAccount = findAccountByAccountNumber(accNumber);
        if(targetAccount == null){
            throw new IllegalArgumentException("Target account not found.");
        }
        accountRepository.transfer(targetAccount.getId(), amount, account.getId());

    }
}
