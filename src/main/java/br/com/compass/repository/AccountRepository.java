package br.com.compass.repository;

import br.com.compass.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class AccountRepository {

    private final EntityManager entityManager;

    public AccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
/*
    public Account save(Account account) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(account);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println("Unexpected error while trying to save to database");
        }
        return account;
    }*/

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
            account.deposit(amount);
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
            sourceAccount.transfer(targetAccount, amount);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("Unexpected error while trying to save to database");
            throw e;
        }
    }

}
