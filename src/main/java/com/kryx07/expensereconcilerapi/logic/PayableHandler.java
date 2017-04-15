package com.kryx07.expensereconcilerapi.logic;

import com.kryx07.expensereconcilerapi.model.transactions.Payable;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PayableHandler {
    public BigDecimal getFractionalAmount(Transaction transaction) {

        return transaction.isCommon() ?
                transaction.getAmount().divide(BigDecimal.valueOf(transaction.getReconcilingUsers().size())) :
                transaction.getAmount().divide(BigDecimal.valueOf(transaction.getReconcilingUsers().size() - 1));
    }

    public Map<String, Payable> getPayables(Transaction transaction) {

        Map<String, Payable> payables = new HashMap<>();

        transaction
                .getReconcilingUsers()
                .getUsers()
                .values()
                .stream()
                .filter(user -> !user.equals(transaction.getPayer()))
                .forEach(user->payables.put(user.getUserName(),new Payable(transaction.getPayer(),transaction.getFractionalAmount())));

        return payables;
    }


}
