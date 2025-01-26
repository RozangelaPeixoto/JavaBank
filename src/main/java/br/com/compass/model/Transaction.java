package br.com.compass.model;

import br.com.compass.model.enums.TransactionType;

import java.time.LocalDateTime;

public class Transaction {

    private Integer id;
    private TransactionType type;
    private Double amount;
    private LocalDateTime dateTime;
    private Account account;

    public Transaction() {}

    public Transaction(Integer id, TransactionType type, Double amount, Account account) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "Transation{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                ", account=" + account +
                '}';
    }
}
