package br.com.compass.service;

import br.com.compass.model.Session;
import br.com.compass.model.Transaction;
import br.com.compass.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getBankStatement() {
        Optional<List<Transaction>> transactionsOptional = transactionRepository.findByAccountId(Session.getUserAccount().getId());
        return transactionsOptional.orElse(null);
    }

}
