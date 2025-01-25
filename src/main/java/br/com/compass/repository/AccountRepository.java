package br.com.compass.repository;

import br.com.compass.model.Account;
import br.com.compass.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    /*
    public Optional<Account> findByUser(User user) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.cpf = :cpf", User.class);
            query.setParameter("cpf", cpf);
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }*/

}
