package com.kryx07.expensereconcilerapi.model.payables;

import com.kryx07.expensereconcilerapi.model.users.Users;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Payables implements Serializable {

    private long serialVersionUID = 13862425326548l;

    private List<Payable> payables = new ArrayList<>();

    public Payables(List<Payable> payables) {
        this.payables=payables;
    }

    public Payables(){

    }

    public List<Payable> getPayables() {
        return payables;
    }

    public void setPayables(List<Payable> payables) {
        this.payables = payables;
    }

    public void add(Payable payable){
        payables.add(payable);
    }

    public Users getUsers(){
        Users payers =  new Users(payables.stream().map(p->p.getPayer()).collect(Collectors.toSet()));
        Users debtors =  new Users(payables.stream().map(p->p.getDebtor()).collect(Collectors.toSet()));

        Users allUsers = new Users(new HashSet<>());
        allUsers.addAll(debtors);
        allUsers.addAll(payers);

        allUsers.distinct();

        return allUsers;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}