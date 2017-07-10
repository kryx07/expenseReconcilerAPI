package com.kryx07.expensereconcilerapi.controllers;

import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.services.TransactionsService;
import com.kryx07.expensereconcilerapi.utils.StringUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
                        ("TransactionOutput with id:" + id + " has not been found!"), HttpStatus.NOT_FOUND) :
                new ResponseEntity<Transaction>(transactionsService.getTransactionById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/by-user", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Transactions> getTransactionsByUser(@RequestParam String username) {
        return transactionsService.getTransactionsByUser(username) == null
                ? new ResponseEntity<Transactions>(transactionsService
                .createTransactionsWithError("There are no transactions!"), HttpStatus.NOT_FOUND)
                : new ResponseEntity<Transactions>(transactionsService.getTransactionsByUser(username), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {

        if (!transactionsService.addTransaction(transaction)) {
            return ResponseEntity.badRequest().build();
        }

        URI uri = StringUtilities.buildUri(ServletUriComponentsBuilder.fromCurrentRequestUri().build() + "/" + transaction.getId());

        return transactionsService.contains(transaction.getId()) ? ResponseEntity.created(uri).build()
                : ResponseEntity.badRequest().build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Transaction> updateTransaction(@RequestParam String id, @RequestBody Transaction newTransactionInput) {

        newTransactionInput.setId(id);

        URI uri = StringUtilities.buildUri(ServletUriComponentsBuilder.fromCurrentRequestUri().build() + "/" + id);

        return transactionsService.update(id, newTransactionInput) ?
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
