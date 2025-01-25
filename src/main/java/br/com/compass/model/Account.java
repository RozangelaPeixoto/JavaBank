package br.com.compass.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "id_type", discriminatorType = DiscriminatorType.INTEGER)
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "acc_number")
    private String accNumber;
    private Double balance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User holder;

    public Account(Integer id, User holder) {
        this.id = id;
        this.accNumber = null;
        this.balance = 0.0;
        this.holder = holder;
    }

    public Account() {

    }

    @PostPersist
    private void generateAccNumber() {
        this.accNumber = String.format("%06d", id);
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

    public Double getBalance() {
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accNumber='" + accNumber + '\'' +
                ", balance=" + balance +
                ", holder=" + holder +
                '}';
    }

    //public List<Transation> bankStatement(){return transactions}


}
