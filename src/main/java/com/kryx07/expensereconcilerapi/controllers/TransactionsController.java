package com.kryx07.expensereconcilerapi.controllers;

import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.services.TransactionsService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    @Autowired
    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Transactions> getAllTransactions() {
        return transactionsService.getAllTransactions() == null
                ? new ResponseEntity<Transactions>(transactionsService
                    .createTransactionsWithError("There are no transactions!"), HttpStatus.NOT_FOUND)
                : new ResponseEntity<Transactions>(transactionsService.getAllTransactions(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        return transactionsService.getTransactionById(id) == null ?
                new ResponseEntity<Transaction>(transactionsService.createTransactionWithError
                        ("Transaction with id:" + id + " has not been found!"), HttpStatus.NOT_FOUND) :
                new ResponseEntity<Transaction>(transactionsService.getTransactionById(id), HttpStatus.OK);
    }

   /* @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Transactions> getBookByAuthor(@RequestParam String author) {
        Transactions transactionsByAuthor = transactionsService.getBookByAuthor(author);
        return transactionsByAuthor == null ?
                new ResponseEntity<Transactions>(transactionsService
                        .createTransactionsWithError("No books of " + author + " have been found!"), HttpStatus.NOT_FOUND) :
                new ResponseEntity<Transactions>(transactionsService.getBookByAuthor(author), HttpStatus.OK);
    }*/

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {

        transactionsService.addTransaction(transaction);

        URI uri = null;
        try {
            uri = new URI(ServletUriComponentsBuilder.fromCurrentRequestUri().build() + "/" + transaction.getId());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return transactionsService.contains(transaction.getId()) ? ResponseEntity.created(uri).build()
                : ResponseEntity.badRequest().build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Transaction> updateTransaction(@RequestParam String id, @RequestBody Transaction newTransaction) {
        URI uri = null;
        try {
            uri = new URI(ServletUriComponentsBuilder.fromCurrentRequestUri().build() + "/" + id);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return transactionsService.update(id, newTransaction) ?
                ResponseEntity.created(uri).build() :
                ResponseEntity.badRequest().build();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTransaction(@PathVariable String id) {
        return transactionsService.delete(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();


    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllTransaction() {
        return transactionsService.deleteAll() ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();


    }
}
