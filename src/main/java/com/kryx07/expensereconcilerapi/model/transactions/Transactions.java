package com.kryx07.expensereconcilerapi.model.transactions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transactions implements Serializable {

    public Transactions(Map<String, Transaction> transactions) {
        this.transactions = transactions;
    }

    private long serialVersionUID = 83877944648906l;

    private Map<String, Transaction> transactions = new HashMap<>();

    @ApiModelProperty(hidden = true)
    @JsonProperty(value = "errorMessage", access = JsonProperty.Access.READ_ONLY)
    private String errorMessage;


    public String getErrorMessage() {
        return errorMessage;
    }


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public Map<String, Transaction> getTransactions() {
        return transactions;
    }


    public void setTransactions(Map<String, Transaction> transactions) {
        this.transactions = transactions;
    }

    public String addTransaction(Transaction transaction) {

        transaction.setId(UUID.randomUUID().toString());
        transactions.put(transaction.getId(), transaction);
        return transaction.getId();
    }

    public boolean deleteBook(String id) {
        try {
            transactions.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Transaction get(String id) {
        return transactions.get(id);
    }

    public boolean update(String id, Transaction newTransaction) {
        try {
            transactions.replace(id, newTransaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean contains(Transaction transaction) {
        System.out.println(transactions.containsValue(transaction));
        return transactions.containsValue(transaction);
    }

    public boolean contains(String id) {
        return transactions.containsKey(id);
    }

    @Override
    public String toString() {
        return "Books{" +
                "serialVersionUID=" + serialVersionUID +
                ", transactions=" + transactions +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
