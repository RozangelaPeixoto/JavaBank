package br.com.compass.util;

import br.com.compass.repository.AccountRepository;
import br.com.compass.repository.UserRepository;
import br.com.compass.service.AccountService;
import br.com.compass.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {

	private static final String PERSISTENCE_UNIT_NAME = "java-bank";
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    static UserRepository userRepository;
    static AccountRepository accountRepository;
 
    private Connection() {}
   
    private static void initialize() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
            userRepository = new UserRepository(entityManager);
            accountRepository = new AccountRepository(entityManager);
        }
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            initialize();
        }
        return entityManager;
    }

    public static void create() {
        initialize();
    }

    public static UserService getUserService() {
        return new UserService(userRepository);
    }

    public static AccountService getAccountService() {
        return new AccountService(accountRepository);
    }
 
    public static void close() {
        if (entityManager != null) {
            entityManager.close();
            entityManager = null;
        }
        if (factory != null) {
            factory.close();
            factory = null;
        }
    }
	
}
