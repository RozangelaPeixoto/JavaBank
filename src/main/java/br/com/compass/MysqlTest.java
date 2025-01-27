package br.com.compass;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.compass.model.Transaction;
import br.com.compass.repository.TransactionRepository;
import br.com.compass.service.TransactionService;
import br.com.compass.util.Connection;

public class MysqlTest {
	 
    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            EntityManager em = Connection.getEntityManager();
            TransactionRepository TransactionRepository = new TransactionRepository(em);
            TransactionService TransactionService = new TransactionService(TransactionRepository);
           // em.getTransaction().begin();
            DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
            //Query query = em.createNativeQuery("SELECT 1");
            //List result = query.getResultList();
            System.out.println();
            System.out.println("======= Bank Statement ======");
            List<Transaction> transactionList = TransactionService.getBankStatement();
            for(Transaction trans : transactionList){
                System.out.println(trans);
            }
            System.out.println();

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
