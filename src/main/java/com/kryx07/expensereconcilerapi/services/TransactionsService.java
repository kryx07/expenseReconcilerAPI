package com.kryx07.expensereconcilerapi.services;

import com.kryx07.expensereconcilerapi.logic.PayableHandler;
import com.kryx07.expensereconcilerapi.utils.FileProcessor;
import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.model.users.Users;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionsService {

    private FileProcessor<Transactions> transactionsFileProcessor;
    private UsersService usersService;
    private PayableHandler payableHandler;

    public TransactionsService() {
        this.transactionsFileProcessor = new FileProcessor<>("transactions.o");
        this.usersService = new UsersService();
        this.payableHandler=new PayableHandler();
    }

    public Transaction getTransactionById(String id) {
        Transactions transactions = transactionsFileProcessor.readAll();
        return transactions == null ? null : transactionsFileProcessor.readAll().get(id);
    }

    public Transactions getAllTransactions() {
        return transactionsFileProcessor.readAll();
    }


    public boolean addTransaction(Transaction transaction) {

        Transactions transactions = transactionsFileProcessor.readAll();
        if (transactions == null) {
            transactions = new Transactions(new HashMap<String, Transaction>());
        }

        Users allUsers = usersService.getAllUsers();
        if (!transaction
                .getReconcilingUsers()
                .getUsers()
                .stream()
                .allMatch(u -> allUsers.contains(u))) {
            return false;
        }

        transaction.setFractionalAmount(payableHandler.getFractionalAmount(transaction));
        //transaction.setFractionalAmount(BigDecimal.ONE);
        transaction.setPayables(payableHandler.getPayables(transaction));

        //BigDecimal fractionalAmount = payableHandler.getFractionalAmount(transaction);

        transactions.addTransaction(transaction);
        transactionsFileProcessor.save(transactions);
        return true;

    }

    public boolean contains(Transaction transaction) {
        return transactionsFileProcessor.readAll().contains(transaction);
    }

    public boolean contains(String id) {
        Transactions transactions =Optional.ofNullable(
                transactionsFileProcessor.readAll())
                .orElse(new Transactions(new HashMap<>()));
        return Optional.ofNullable(transactions.contains(id)).orElse(false);
    }

    public Transaction createTransactionWithError(String errorMessage) {
        Transaction transaction = new Transaction();
        transaction.setErrorMessage(errorMessage);
        return transaction;
    }

    public Transactions createTransactionsWithError(String errorMessage) {
        Transactions transactions = new Transactions(null);
        transactions.setErrorMessage(errorMessage);
        return transactions;
    }

    public boolean update(String id, Transaction newTransaction) {
        Transactions transactions = transactionsFileProcessor.readAll();
        if (transactions == null || !transactions.contains(id)) {
            return false;
        }
        boolean isUpdated = transactions.update(id, newTransaction);
        transactionsFileProcessor.save(transactions);
        return isUpdated;
    }

    public boolean delete(String id) {
        Transactions transactions = transactionsFileProcessor.readAll();
        if (transactions == null || !transactions.contains(id)) {
            return false;
        }
        boolean isDeleted = transactions.deleteBook(id);
        System.out.println(transactionsFileProcessor.readAll());
        transactionsFileProcessor.save(transactions);
        return isDeleted;
    }

    public boolean deleteAll() {
        transactionsFileProcessor.save(new Transactions(new HashMap<>()));
        return true;
    }
}
