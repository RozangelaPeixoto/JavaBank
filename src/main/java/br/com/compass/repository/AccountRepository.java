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

    public boolean existAccount(String cpf, String password){
        String jpql = "SELECT COUNT(a) FROM Account a WHERE a.holder.cpf = :cpf AND a.password = :password";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("cpf", cpf);
        query.setParameter("password", password);
        Long count = query.getSingleResult();
        return count > 0;
    }

    public Account findByUserCpf(String cpf) {
        try {
            TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a WHERE a.holder.cpf = :cpf", Account.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Account findByAccountNumber(String accNumber) {
        try {
            TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a WHERE a.accNumber = :number", Account.class);
            query.setParameter("number", accNumber);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
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
            throw e;
        }
    }

    public void withdraw(Double amount, Integer id) {

        try{
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, id);
            if (amount > account.getBalance()) {
                throw new IllegalArgumentException("Insufficient balance.");
            }
            account.withdraw(amount);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }

    }

    public void transfer(Integer targetAccId, Double amount, Integer id){
        try{
            entityManager.getTransaction().begin();
            Account sourceAccount = entityManager.find(Account.class, id);
            Account targetAccount = entityManager.find(Account.class, targetAccId);
            sourceAccount.transfer(targetAccount, amount);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

}
