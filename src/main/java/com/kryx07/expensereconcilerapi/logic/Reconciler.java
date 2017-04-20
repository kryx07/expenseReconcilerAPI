package com.kryx07.expensereconcilerapi.logic;

import com.kryx07.expensereconcilerapi.model.payables.Payable;
import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.reconciliations.Reconciliation;
import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.utils.FileProcessor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reconciler {

    private FileProcessor<Transactions> transactionsFileProcessor;

    public Reconciler() {
        this.transactionsFileProcessor = new FileProcessor<>("transactions.o");
    }

    public Set<Users> getReconciliationParties() {
        return transactionsFileProcessor
                .readAll()
                .getTransactions()
                .values()
                .stream()
                .map(t -> t.getReconcilingUsers())
                .distinct()
                .collect(Collectors.toSet());
    }

    public Payables getPayablesByReconcilingParties(Users users) {

        Transactions reconcilingTransactions = getReconcilingTransactions(users);

        Payables payables = new Payables();

        Stream<User> userStream = users.getUsers().stream();

        userStream
                .forEach(user1 -> userStream
                        .filter(user2->!user2.equals(user1))
                        .forEach(user2->payables.add(user1.getUserName(),new Payable(user2, BigDecimal.ZERO))));

        return payables;
    }

    private Transactions getReconcilingTransactions(Users users) {
        return new Transactions(transactionsFileProcessor
                .readAll()
                .getTransactions()
                .values()
                .stream()
                .filter(t -> t.getReconcilingUsers().equals(users))
                .collect(Collectors.toMap(t -> t.getId(), t -> t)));
    }


}
