package com.kryx07.expensereconcilerapi.model.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transactions implements Serializable {

    private long serialVersionUID = 83877944648906l;

    private List<Transaction> transactions = new ArrayList<>();

    @ApiModelProperty(hidden = true)
    //@JsonProperty(value = "errorMessage", access = JsonProperty.Access.READ_ONLY)
    private String errorMessage;

    public Transactions(ArrayList<Transaction> transactions) {
        this.transactions=transactions;
    }

    public Transactions() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String addTransaction(Transaction transaction) {

        transaction.setId(UUID.randomUUID().toString());
        transactions.add(transaction);
        return transaction.getId();
    }

    public boolean deleteTransaction(String id) {
        Transaction transaction = this.get(id);
        if (transaction == null) {
            return false;
        }
        transactions.remove(transaction);
        return !transactions.contains(transaction);
    }

    public boolean deleteTransaction(Transaction transaction) {
        transactions.remove(transaction);
        return !transactions.contains(transaction);
    }

    public Transaction get(String id) {
        return transactions
                .stream()
                .filter(t -> t.getId().equals(id))
                .findFirst().get();
    }

    public boolean update(String id, Transaction newTransaction) {

        int index = getIndex(get(id));
        if (index < 0) {
            return false;
        }

        return set(index, newTransaction);
    }

    public boolean update(Transaction oldTransaction, Transaction newTransaction) {
        int index = getIndex(oldTransaction);
        return set(index, newTransaction);
    }

    private boolean set(int index, Transaction transaction) {
        transactions.set(index, transaction);
        return transactions.contains(transaction);
    }

    private int getIndex(Transaction transaction) {
        for (int i = 0; i < transactions.size(); ++i) {
            if (transactions.get(i).equals(transaction)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(Transaction transaction) {
        return transactions.contains(transaction);
    }

    public boolean contains(String id) {
        if (transactions == null){
            return false;
        }
        return transactions.contains(get(id));
    }

    public int size(){
        return transactions.size();
    }
    @JsonIgnore
    public boolean isEmpty(){
        return transactions==null || errorMessage!=null;
    }

    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }

}
