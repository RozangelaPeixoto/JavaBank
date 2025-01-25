package br.com.compass.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final AtomicInteger accountCounter = new AtomicInteger(1);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @SequenceGenerator(name = "acc_seq", sequenceName = "acc_sequence", allocationSize = 1)
    private Integer id;
    private String accNumber;
    private Double balance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
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
