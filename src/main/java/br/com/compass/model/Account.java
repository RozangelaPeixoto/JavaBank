package br.com.compass.model;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Account {

    private static final AtomicInteger accountCounter = new AtomicInteger(1);
    private Integer id;
    private String accNumber;
    private Double balance;
    private User holder;

    public Account(Integer id, Double balance, User holder) {
        this.id = id;
        this.accNumber = generateAccNumber();
        this.balance = balance;
        this.holder = holder;
    }

    private String generateAccNumber() {
        int uniqueNumber = accountCounter.getAndIncrement();
        return String.format("%06d", uniqueNumber);
    }

    public Integer getId() {
        return id;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public User getHolder() {
        return holder;
    }

    public void deposit(double value){
        if (value <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        balance += value;
        //registrar nas transaçoes
    }

    public void withdraw(double value){
        if (value <= 0) {
            throw new IllegalArgumentException("The withdrawal amount must be greater than zero.");
        }
        if (value > balance) {
            throw new IllegalArgumentException("Insufficient balance to make withdrawal.");
        }
        balance -= value;
        //registrar nas transaçoes
    }

    public Double checkBalance() {
        return balance;
    }

    public void transfer(Account account, double value){
        if (account == null) {
            throw new IllegalArgumentException("Invalid destination account.");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("The transfer amount must be greater than zero.");
        }
        if (value > balance) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        balance -= value;
        account.deposit(value);
        //registrar nas transaçoes
    }

    //public List<Transation> bankStatement(){return transactions}


}
