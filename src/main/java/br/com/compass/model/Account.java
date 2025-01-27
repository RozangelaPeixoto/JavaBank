package br.com.compass.model;

import br.com.compass.model.enums.TransactionType;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number")
    private String accNumber;
    private Double balance;

    @OneToOne
    @JoinColumn(name = "user_cpf", referencedColumnName = "cpf", unique = true, nullable = false)
    private User holder;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Account() {
        this.id = null;
        this.accNumber = null;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    @PostPersist
    private void generateAccNumber() {
        this.accNumber = String.format("%06d", id);
    }

    @PrePersist
    private void prePersist() {
        if (holder != null) {
            this.userId = holder.getId();
        }
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
        addTransaction(TransactionType.DEPOSIT, value, null);
    }

    public void withdraw(double value){
        balance -= value;
        addTransaction(TransactionType.WITHDRAW, value, null);
    }

    public Double getBalance() {
        return balance;
    }

    public void transfer(Account targetAccount, double value){
        withdraw(value);
        targetAccount.deposit(value);
        addTransaction(TransactionType.TRANSFER, value, targetAccount);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(TransactionType type, Double amount, Account targetAccount){
        Long idT = System.currentTimeMillis();
        Transaction transaction = new Transaction(
                new TransactionId(idT, this.id),
                type,
                (type == TransactionType.DEPOSIT ? amount : -amount),
                LocalDateTime.now(),
                this
        );
        transactions.add(transaction);
        if(type == TransactionType.TRANSFER){
            Transaction targetTransaction = new Transaction(
                    new TransactionId(idT, targetAccount.getId()),
                    type,
                    amount,
                    LocalDateTime.now(),
                    targetAccount
            );
            transactions.add(targetTransaction);
        }
    }

    public void setHolder(User holder){
        this.holder = holder;
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

}
