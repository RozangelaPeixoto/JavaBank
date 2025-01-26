package br.com.compass.repository;

import br.com.compass.model.Account;
import br.com.compass.model.Transaction;
import br.com.compass.model.TransactionId;
import br.com.compass.model.enums.TransactionType;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

public class AccountRepository {

    private final EntityManager entityManager;

    public AccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account save(Account account) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(account);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println("Unexpected error while trying to save to database");
        }
        return account;
    }

    public Optional<Account> findByUserId(Integer id) {
        try {
            TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a WHERE a.holder.id = :id", Account.class);
            query.setParameter("id", id);
            Account account = query.getSingleResult();
            return Optional.of(account);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Account> findByAccountNumber(String accNumber) {
        try {
            TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a WHERE a.accNumber = :number", Account.class);
            query.setParameter("number", accNumber);
            Account account = query.getSingleResult();
            return Optional.of(account);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void deposit(Double amount, Integer id) {

        try{
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, id);
            Long idT = System.currentTimeMillis();
            Transaction transaction = new Transaction(
                    new TransactionId(idT, account.getId()),
                    TransactionType.DEPOSIT,
                    amount,
                    LocalDateTime.now(),
                    account
            );
            entityManager.persist(transaction);
            account.deposit(amount);
            //Session.setUserAccount(account);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("Unexpected error while trying to save to database");
            throw e;
        }
    }

    public void withdraw(Double amount, Integer id) {

        try{
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, id);
            Long idT = System.currentTimeMillis();
            Transaction transaction = new Transaction(
                    new TransactionId(idT, account.getId()),
                    TransactionType.WITHDRAW,
                    -amount,
                    LocalDateTime.now(),
                    account
            );
            entityManager.persist(transaction);
            account.withdraw(amount);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("Unexpected error while trying to save to database");
            throw e;
        }

    }

    public Double balance(Integer id) {
        try{
            Account account = entityManager.find(Account.class, id);
            return account.getBalance();
        }catch(Exception e){
            System.out.println("Unexpected error accessing database");
            throw e;
        }
    }

    public void transfer(Integer idTargetAcc, Double amount, Integer id){
        try{
            entityManager.getTransaction().begin();
            Account sourceAccount = entityManager.find(Account.class, id);
            Account targetAccount = entityManager.find(Account.class, idTargetAcc);
            Long idT = System.currentTimeMillis();
            Transaction sourceTransaction = new Transaction(
                    new TransactionId(idT, sourceAccount.getId()),
                    TransactionType.TRANSFER,
                    -amount,
                    LocalDateTime.now(),
                    sourceAccount
            );
            Transaction targetTransaction = new Transaction(
                    new TransactionId(idT, targetAccount.getId()),
                    TransactionType.TRANSFER,
                    amount,
                    LocalDateTime.now(),
                    targetAccount
            );
            entityManager.persist(sourceTransaction);
            entityManager.persist(targetTransaction);
            sourceAccount.transfer(targetAccount, amount);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("Unexpected error while trying to save to database");
            throw e;
        }
    }
}
