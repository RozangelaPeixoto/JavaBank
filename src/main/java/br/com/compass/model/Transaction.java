package br.com.compass.model;

import br.com.compass.model.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Transaction {

    static DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    @EmbeddedId
    private TransactionId id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private Double amount;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "id_acc")
    private Account account;

    public Transaction() {}

    public Transaction(TransactionId id, TransactionType type, Double amount, LocalDateTime dateTime, Account account) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
        this.account = account;
    }

    public TransactionId getId() {
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
        String type = getType() == TransactionType.DEPOSIT ? "DEPOSIT " : String.valueOf(getType());
        return getDateTime().format(formatDateTime)
                + " - " + type + " - R$ " +
                String.format("%.2f", getAmount());
    }
}
