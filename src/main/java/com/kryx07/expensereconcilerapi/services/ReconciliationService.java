package com.kryx07.expensereconcilerapi.services;

import com.kryx07.expensereconcilerapi.model.payables.Payable;
import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.model.transactions.Transactions;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.UserGroups;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.utils.FileProcessor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReconciliationService {


    private TransactionsService transactionsService;
    private FileProcessor<UserGroups> reconcilingUserGroupsFileProcessor;


    public ReconciliationService() {
        this.reconcilingUserGroupsFileProcessor = new FileProcessor<>("reconcilingUsers.o");
        this.transactionsService = new TransactionsService();
    }


    public UserGroups getAllReconcilingUserGroups() {
        /*return Optional.ofNullable(reconcilingUserGroupsFileProcessor
                .readAll())
                .orElse(new UserGroups());*/
        return reconcilingUserGroupsFileProcessor.readAll();
    }

    public Users getReconcilingUsersByGroupId(String id) {
        return reconcilingUserGroupsFileProcessor.readAll().get(id);
    }

    public Payables getPayablesByReconcilingUsers(Users users) {
        Transactions reconcilingTransactions = getReconcilingTransactions(users);

        Payables payables = new Payables();

        getUsersStream(users)
                .forEach(user1 -> getUsersStream(users)
                        .filter(user2 -> !user2.equals(user1))
                        .forEach(user2 -> payables.add(calculatePayable(user1,user2,reconcilingTransactions))));

        return payables;
    }

    private Stream<User> getUsersStream(Users users) {
        return users.getUsers().stream();
    }

    private Transactions getReconcilingTransactions(Users users) {
        return new Transactions(new ArrayList<>(transactionsService.getAllTransactions()
                .getTransactions()
                .stream()
                .filter(t -> t.getTransactionParties().equals(users))
                .collect(Collectors.toList())));
    }

    private Payable calculatePayable(User payer, User debtor, Transactions transactions) {
        Payable payable = new Payable(payer, debtor, BigDecimal.ZERO);
        Payables payables = extractPayablesFromTransactions(transactions);

        payables.getPayables().stream()
                .filter(p -> p.getPayer().equals(payer) && p.getDebtor().equals(debtor))
                .forEach(p -> payable.setAmount(payable.getAmount().add(p.getAmount())));

        payables.getPayables().stream()
                .filter(p -> p.getPayer().equals(debtor) && p.getDebtor().equals(payer))
                .forEach(p -> payable.setAmount(payable.getAmount().subtract(p.getAmount())));

        return payable;
    }

    private Payables extractPayablesFromTransactions(Transactions transactions) {
        return new Payables(
                new ArrayList<Payable>(
                        transactions.getTransactions()
                                .stream()
                                .map(t -> t.getPayables().getPayables())
                                .flatMap(p -> p.stream())
                                .collect(Collectors.toList())));
    }
}
