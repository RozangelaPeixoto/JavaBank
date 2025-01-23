package br.com.compass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.compass.model.User;
import br.com.compass.util.Conn;

public class MysqlTest {
	 
    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            // Obter o EntityManager
            EntityManager em = Conn.getEntityManager();
 
            // Iniciar transação
            em.getTransaction().begin();
 
         // Executar uma consulta simples para verificar a conexão
            Query query = em.createNativeQuery("SELECT 1");
            List result = query.getResultList();

            LocalDate birthDate = LocalDate.parse("25/07/2016", formatter);
            User u1 = new User(null, "Adam", "12548536521", birthDate, "757252274", "adam@gmail.com", "123!@#");
            em.persist(u1);
 
            // Verificar o resultado da consulta
            if (!result.isEmpty() && result.get(0).equals(1)) {
                System.out.println("Conexão com o banco de dados foi bem-sucedida!");
            } else {
                System.out.println("Erro ao conectar com o banco de dados.");
            }
 
            // Commit da transação
            em.getTransaction().commit();
 
            // Fechar o EntityManager e o EntityManagerFactory
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
