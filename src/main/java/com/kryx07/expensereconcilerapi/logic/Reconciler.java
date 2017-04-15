package com.kryx07.expensereconcilerapi.logic;

import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.utils.FileProcessor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Reconciler {

   private FileProcessor<Transactions> transactionsFileProcessor;

    public Reconciler() {
        this.transactionsFileProcessor = new FileProcessor<>("transactions.o");
    }

    public Set<Users> getReconciliationParties(){
        return transactionsFileProcessor
                .readAll()
                .getTransactions()
                .values()
                .stream()
                .map(t->t.getReconcilingUsers())
                .distinct()
                .collect(Collectors.toSet());
    }
}
