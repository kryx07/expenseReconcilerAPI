package com.kryx07.expensereconcilerapi.model.payables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Payables implements Serializable{

    private long serialVersionUID = 13862425326548l;
    private Map<String,Payable> payables= new HashMap<>();

    public Payables(Map<String, Payable> payables) {
        this.payables = payables;
    }

    public Payables() {
    }

    public Map<String, Payable> getPayables() {
        return payables;
    }

    public void setPayables(Map<String, Payable> payables) {
        this.payables = payables;
    }

    public void add(String userName,Payable payable){
        payables.put(userName,payable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payables payables1 = (Payables) o;

        return payables != null ? payables.equals(payables1.payables) : payables1.payables == null;
    }

    @Override
    public int hashCode() {
        return payables != null ? payables.hashCode() : 0;
    }
}
