package com.kryx07.expensereconcilerapi.services;

import com.kryx07.expensereconcilerapi.logic.BooksFileHandler;
import com.kryx07.expensereconcilerapi.model.Transactions;
import com.kryx07.expensereconcilerapi.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class TransactionsService {

    private BooksFileHandler booksFileHandler = new BooksFileHandler();

    public Transaction getBookById(String id) {
        Transactions transactions = booksFileHandler.readAll();
        return transactions == null ? null : booksFileHandler.readAll().get(id);
    }

    public Transactions getAllBooks() {
        return booksFileHandler.readAll();
    }


    public boolean addBook(Transaction transaction) {

        Transactions transactions = booksFileHandler.readAll();
        if (transactions == null) {
            transactions = new Transactions(new HashMap<String, Transaction>());
        }

        transactions.addBook(transaction);
        booksFileHandler.save(transactions);

        return booksFileHandler.readAll().contains(transaction.getId());
    }

    public boolean contains(Transaction transaction) {
        return booksFileHandler.readAll().contains(transaction);
    }

    public boolean contains(String id) {
        return booksFileHandler.readAll().contains(id);
    }

    public Transaction createBookWithError(String errorMessage) {
        Transaction transaction = new Transaction();
        transaction.setErrorMessage(errorMessage);
        return transaction;
    }

    public Transactions createBooksWithError(String errorMessage) {
        Transactions transactions = new Transactions(null);
        transactions.setErrorMessage(errorMessage);
        return transactions;
    }

    public Transactions getBookByAuthor(String author) {
        Transactions transactions = booksFileHandler.readAll();
        return transactions == null ?
                null :
                new Transactions(transactions
                        .getBookMap()
                        .values()
                        .stream()
                        .filter(b -> b.getAuthor()
                                .equals(author))
                        .collect(Collectors
                                .toMap(transaction -> transaction.getId(), x -> x)));
    }

    public boolean update(String id, Transaction newTransaction) {
        Transactions transactions = booksFileHandler.readAll();
        if (transactions == null || !transactions.contains(id)) {
            return false;
        }
        boolean isUpdated = transactions.update(id, newTransaction);
        booksFileHandler.save(transactions);
        return isUpdated;
    }

    public boolean delete(String id) {
        Transactions transactions = booksFileHandler.readAll();
        if (transactions == null || !transactions.contains(id)) {
            return false;
        }
        boolean isDeleted = transactions.deleteBook(id);
        System.out.println(booksFileHandler.readAll());
        booksFileHandler.save(transactions);
        return isDeleted;
    }
}
