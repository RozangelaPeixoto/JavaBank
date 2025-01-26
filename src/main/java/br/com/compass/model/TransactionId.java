package br.com.compass.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TransactionId implements Serializable {

    private Long id;
    private Integer accountId;

    public TransactionId() {}

    public TransactionId(Long id, Integer accountId) {
        this.id = id;
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionId that = (TransactionId) o;

        if (!id.equals(that.id)) return false;
        return accountId.equals(that.accountId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + accountId.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public Integer getAccountId() {
        return accountId;
    }
}
