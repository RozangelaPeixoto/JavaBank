package br.com.compass.repository;

import br.com.compass.model.Account;
import br.com.compass.model.Session;
import br.com.compass.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
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

    public void deposit(Double amount, Integer id) {

        try{
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, id);
            account.deposit(amount);
            Session.setUserAccount(account);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("Unexpected error while trying to save to database");
            throw e;
        }
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

    public Double balance(Integer id) {
        try{
            Account account = entityManager.find(Account.class, id);
            Session.setUserAccount(account);
            return account.getBalance();
        }catch(Exception e){
            System.out.println("Unexpected error accessing database");
            throw e;
        }
    }
}
