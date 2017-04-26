package com.kryx07.expensereconcilerapi.services;

import com.kryx07.expensereconcilerapi.logic.PayableHandler;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.model.users.UserGroups;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.utils.FileProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.kryx07.expensereconcilerapi.services.errorhandling.ErrorCodes.NO_TRANSACTIONS;
import static com.kryx07.expensereconcilerapi.services.errorhandling.ErrorCodes.NO_TRANSACTION_WITH_ID;

@Service
public class TransactionsService {

    private FileProcessor<Transactions> transactionsFileProcessor;
    private FileProcessor<UserGroups> reconcilingUserGroupsFileProcessor;
    private UsersService usersService;

    public TransactionsService() {
        this.transactionsFileProcessor = new FileProcessor<>("transactions.o");
        this.reconcilingUserGroupsFileProcessor = new FileProcessor<>("reconcilingUsers.o");
        this.usersService = new UsersService();
    }

    public Transaction getTransactionById(String id) {
        Transactions allTransactions = getAllTransactions();
        return (allTransactions.isEmpty())
                ? createTransactionWithError(NO_TRANSACTIONS.toString())
                : Optional
                .ofNullable(allTransactions.get(id))
                .orElse(createTransactionWithError(NO_TRANSACTION_WITH_ID.toString() + id));
    }

    public Transactions getAllTransactions() {
        return Optional
                .ofNullable(transactionsFileProcessor.readAll())
                .orElse(createTransactionsWithError(NO_TRANSACTIONS.toString()));
    }

    public boolean addTransaction(Transaction transaction) {

        Transactions allTransactions = getAllTransactions();
        if (allTransactions.isEmpty()) {
            allTransactions = new Transactions(new ArrayList<>());
        }

        Users allUsers = usersService.getAllUsers();
        /*if (allUsers.isEmpty()){
            return false;
        }*/
        if (!transaction
                .getTransactionParties()
                .getUsers()
                .stream()
                .allMatch(allUsers::contains)){
            return false;
        }

        transaction.setPayables(new PayableHandler(transaction).getPayables());

        allTransactions.addTransaction(transaction);

        UserGroups allUserGroups =
                Optional.ofNullable(reconcilingUserGroupsFileProcessor.readAll())
                        .orElse(new UserGroups());
        allUserGroups.add(transaction.getTransactionParties());

        reconcilingUserGroupsFileProcessor.save(allUserGroups);
        transactionsFileProcessor.save(allTransactions);

        return true;

    }

    public boolean contains(Transaction transaction) {
        return transactionsFileProcessor.readAll().contains(transaction);
    }

    public boolean contains(String id) {
        Transactions transactions = Optional.ofNullable(
                transactionsFileProcessor.readAll())
                .orElse(new Transactions(new ArrayList<>()));
        return transactions.contains(id);

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
        boolean isDeleted = transactions.deleteTransaction(id);
        System.out.println(transactionsFileProcessor.readAll());
        transactionsFileProcessor.save(transactions);
        return isDeleted;
    }

    public boolean deleteAll() {
        transactionsFileProcessor.save(createTransactionsWithError(NO_TRANSACTIONS.toString()));
        return true;
    }
}
