package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.repository.AccountRepository;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

    public void depositValue(String value, Integer id) {
        Double amount = convertValue(value);
        if(amount == null){
            throw new IllegalArgumentException("Invalid number.");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        accountRepository.deposit(amount, id);
    }

    public void withdrawValue(String value, Integer id){
        Double amount = convertValue(value);
        if(amount == null){
            throw new IllegalArgumentException("Invalid number.");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        accountRepository.withdraw(amount, id);
    }

    public void transferValue(String value, String accNumber, Account account){
        Double amount = convertValue(value);
        if(amount == null){
            throw new IllegalArgumentException("Invalid number.");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        if(account.getAccNumber().equals(accNumber)){
            throw new IllegalArgumentException("Target account and source account cannot be the same.");
        }
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        Account targetAccount = accountRepository.findByAccountNumber(accNumber);
        if(targetAccount == null){
            throw new IllegalArgumentException("Target account not found.");
        }
        accountRepository.transfer(targetAccount.getId(), amount, account.getId());

    }

    public boolean validLogin(String cpf, String password){
        return accountRepository.existAccount(cpf, password);
    }

    public Account getAccount(String cpf){
        return accountRepository.findByUserCpf(cpf);
    }
}
