package br.com.compass;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.compass.model.Account;
import br.com.compass.model.Transaction;
import br.com.compass.model.TransactionId;
import br.com.compass.model.User;
import br.com.compass.model.enums.TransactionType;
import br.com.compass.repository.AccountRepository;
import br.com.compass.util.Conn;

public class MysqlTest {
	 
    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            EntityManager em = Conn.getEntityManager();
            AccountRepository accountRepository = new AccountRepository(em);
           // em.getTransaction().begin();

            //Query query = em.createNativeQuery("SELECT 1");
            //List result = query.getResultList();


           /* Account contaOrigem = em.find(Account.class, 1);

            double valorTransferencia = 20.0;
            accountRepository.deposit(valorTransferencia, contaOrigem.getId());

            System.out.println(contaOrigem);
            em.merge(contaOrigem);*/

            /*
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.holder.id = :id", Account.class);
            query.setParameter("id", 1);
            List<Account> accounts = query.getResultList();

            System.out.println(accounts.get(0).getAccNumber());
            if (!result.isEmpty() && result.get(0).equals(1)) {
                System.out.println("Conexão com o banco de dados bem-sucedida!");
            } else {
                System.out.println("Erro ao conectar com o banco de dados.");
            }*/

            //em.getTransaction().commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
