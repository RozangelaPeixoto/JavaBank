package br.com.compass.model;

import br.com.compass.model.enums.AccountType;
import br.com.compass.model.enums.TransactionType;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number")
    private String accNumber;
    private Double balance;
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @OneToOne
    @JoinColumn(name = "user_cpf", referencedColumnName = "cpf", unique = true, nullable = false)
    private User holder;

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

    public void depositViaTransfer(double value){
        balance += value;
    }

    public void withdraw(double value){
        balance -= value;
        addTransaction(TransactionType.WITHDRAW, value, null);
    }

    public Double getBalance() {
        return balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void transfer(Account targetAccount, double value){
        balance -= value;
        targetAccount.depositViaTransfer(value);
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
