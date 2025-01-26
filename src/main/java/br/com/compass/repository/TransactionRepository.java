package br.com.compass.repository;

import br.com.compass.model.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TransactionRepository {

    private final EntityManager entityManager;

    public TransactionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<List<Transaction>> findByAccountId(Integer idAccount) {
        try {
            TypedQuery<Transaction> query = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.id.accountId = :id", Transaction.class);
            query.setParameter("id", idAccount);
            List<Transaction> transactions = query.getResultList();
            return Optional.of(transactions);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
