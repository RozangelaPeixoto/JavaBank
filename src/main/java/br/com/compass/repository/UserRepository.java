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

    public User save(User user) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return user;
    }

    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    public Optional<User> findByCpf(String cpf) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.cpf = :cpf", User.class);
            query.setParameter("cpf", cpf);
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
