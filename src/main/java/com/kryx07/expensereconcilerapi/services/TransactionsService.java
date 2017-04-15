package com.kryx07.expensereconcilerapi.services;

import com.kryx07.expensereconcilerapi.logic.FileProcessor;
import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionsService {

    private FileProcessor<Transactions> transactionsFileProcessor;
    private UsersService usersService;

    public TransactionsService() {
        this.transactionsFileProcessor = new FileProcessor<>("transactions.o");
        ;
        this.usersService = new UsersService();
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
                .values()
                .stream()
                .allMatch(u -> allUsers.contains(u))) {
            return false;
        }

        transactions.addTransaction(transaction);

        if(transactions.contains(transaction.getId())){
            reconcileTransaction(transaction);
            transactionsFileProcessor.save(transactions);
            return true;
        }

        return false;
    }

    public boolean contains(Transaction transaction) {
        return transactionsFileProcessor.readAll().contains(transaction);
    }

    public boolean contains(String id) {
        return transactionsFileProcessor.readAll().contains(id);
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

    private void reconcileTransaction(Transaction transaction) {
        User payer = transaction.getPayer();
        Users reconcilingUsers = transaction.getReconcilingUsers();
        BigDecimal payback = transaction
                .getAmount()
                .divide(BigDecimal.valueOf(reconcilingUsers.size()));
        reconcilingUsers
                .getUsers()
                .values()
                .stream()
                .filter(user -> !user.equals(payer))
                .forEach(user -> addAmountPayable(user,payer,payback));

    }

    private void addAmountPayable(User user, User payer, BigDecimal amount) {
        Map<String, BigDecimal> amountPayable = user.getAmountPayable();
        if(amountPayable == null){
            amountPayable = new HashMap<String,BigDecimal>();
        }
        if (amountPayable.containsKey(payer)) {
            amountPayable.get(payer).add(amount);
        } else {
            amountPayable.put(payer.getUserName(), amount);
        }

        user.setAmountPayable(amountPayable);
    }
}
