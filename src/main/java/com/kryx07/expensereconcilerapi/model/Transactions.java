package com.kryx07.expensereconcilerapi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transactions implements Serializable {

    public Transactions(Map<String, Transaction> bookMap) {
        this.bookMap = bookMap;
    }

    private long serialVersionUID = 83877944648906l;

    private Map<String, Transaction> bookMap = new HashMap<>();

    @ApiModelProperty(hidden = true)
    @JsonProperty(value = "errorMessage", access = JsonProperty.Access.READ_ONLY)
    private String errorMessage;


    public String getErrorMessage() {
        return errorMessage;
    }


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public Map<String, Transaction> getBookMap() {
        return bookMap;
    }


    public void setBookMap(Map<String, Transaction> bookMap) {
        this.bookMap = bookMap;
    }

    public String addBook(Transaction transaction) {
        transaction.setId(UUID.randomUUID().toString());
        bookMap.put(transaction.getId(), transaction);
        return transaction.getId();
    }

    public boolean deleteBook(String id) {
        try {
            bookMap.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Transaction get(String id) {
        return bookMap.get(id);
    }

    public boolean update(String id, Transaction newTransaction) {
        try {
            bookMap.replace(id, newTransaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean contains(Transaction transaction) {
        System.out.println(bookMap.containsValue(transaction));
        return bookMap.containsValue(transaction);
    }

    public boolean contains(String id) {
        return bookMap.containsKey(id);
    }

    @Override
    public String toString() {
        return "Books{" +
                "serialVersionUID=" + serialVersionUID +
                ", bookMap=" + bookMap +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
