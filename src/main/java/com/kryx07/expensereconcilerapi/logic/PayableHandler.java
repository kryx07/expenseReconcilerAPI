package com.kryx07.expensereconcilerapi.logic;

import com.kryx07.expensereconcilerapi.model.payables.Payable;
import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

public class PayableHandler {

    Transaction transaction;

    public PayableHandler(Transaction transaction) {
        this.transaction = transaction;
    }

    public Payables getPayables() {

        Payables payables = new Payables();

        transaction
                .getTransactionParties()
                .getUsers()
                .stream()
                .filter(user -> !user.equals(transaction.getPayer()))
                .forEach(user -> payables.add(new Payable(transaction.getPayer(), user, getFractionalAmount())));

        return payables;
    }

    private BigDecimal getFractionalAmount() {

        return transaction.isCommon() ?
                transaction.getAmount()
                        .divide(BigDecimal.valueOf(transaction.getTransactionParties().size()), 10, RoundingMode.CEILING) :
                transaction.getAmount()
                        .divide(BigDecimal.valueOf(transaction.getTransactionParties().size() - 1), 10, RoundingMode.CEILING);
    }

    public static void main(String[] args) {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(432.43));
        transaction.setCommon(true);
        transaction.setDate(LocalDate.of(2017, 1, 1));
        transaction.setDescription("Test transaction");
        transaction.setId("This is a test ID");
        User user1 = new User();
        user1.setUserName("Bolek");
        User user2 = new User();
        user2.setUserName("Agent");

        transaction.setPayer(user1);
        transaction.setTransactionParties(new Users(new HashSet(Arrays.asList(user1, user2))));

        System.out.println(new PayableHandler(transaction).getPayables());
    }


}
