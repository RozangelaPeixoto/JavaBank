package br.com.compass.repository;

import br.com.compass.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class UserRepository{

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(User user) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public User findByCpf(String cpf) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.cpf = :cpf", User.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existUser(String cpf){
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.cpf = :cpf";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("cpf", cpf);
        Long count = query.getSingleResult();
        return count > 0;
    }

}
