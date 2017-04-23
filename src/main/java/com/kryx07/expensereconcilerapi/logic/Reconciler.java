package com.kryx07.expensereconcilerapi.logic;

import com.kryx07.expensereconcilerapi.model.payables.Payable;
import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.UserGroups;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.utils.FileProcessor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reconciler {

    private FileProcessor<Transactions> transactionsFileProcessor;
    private FileProcessor<UserGroups> reconcilingUserGroupsFileProcessor;


    public Reconciler() {
        this.transactionsFileProcessor = new FileProcessor<>("transactions.o");
        this.reconcilingUserGroupsFileProcessor = new FileProcessor<>("reconcilingUsers.o");
    }

    /*public Set<Users> getAllReconcilingParties() {
        return transactionsFileProcessor
                .readAll()
                .getTransactions()
                .stream()
                .map(Transaction::getTransactionParties)
                .distinct()
                .collect(Collectors.toSet());
    }*/
    //correct for null
    public UserGroups getAllReconcilingUserGroups(){
        return Optional.ofNullable(reconcilingUserGroupsFileProcessor
                .readAll()).orElse(new UserGroups());
    }


    private Transactions getReconcilingTransactions(Users users) {
        return new Transactions(new ArrayList<>(transactionsFileProcessor
                .readAll()
                .getTransactions()
                .stream()
                .filter(t -> t.getTransactionParties().equals(users))
                .collect(Collectors.toList())));
    }

    public Payables getPayablesByReconcilingParties(Users users) {

        Transactions reconcilingTransactions = getReconcilingTransactions(users);

        Payables payables = new Payables();

        Stream<User> userStream = users.getUsers().stream();

        userStream
                .forEach(user1 -> userStream
                        .filter(user2 -> !user2.equals(user1))
                        .forEach(user2 -> payables.add(new Payable(user1, user2, BigDecimal.ZERO))));

        return payables;
    }


}
