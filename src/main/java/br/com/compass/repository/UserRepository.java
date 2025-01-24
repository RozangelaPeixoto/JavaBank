package br.com.compass.repository;

import br.com.compass.model.User;

import javax.persistence.EntityManager;

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
}
