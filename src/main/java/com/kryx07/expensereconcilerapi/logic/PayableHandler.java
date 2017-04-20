package com.kryx07.expensereconcilerapi.logic;

import com.kryx07.expensereconcilerapi.model.payables.Payable;
import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PayableHandler {
    public BigDecimal getFractionalAmount(Transaction transaction) {

        return transaction.isCommon() ?
                transaction.getAmount().divide(BigDecimal.valueOf(transaction.getReconcilingUsers().size())) :
                transaction.getAmount().divide(BigDecimal.valueOf(transaction.getReconcilingUsers().size() - 1));
    }

    public Payables getPayables(Transaction transaction) {

        Map<String, Payable> payables = new HashMap<>();

        transaction
                .getReconcilingUsers()
                .getUsers()
                .stream()
                .filter(user -> !user.equals(transaction.getPayer()))
                .forEach(user->payables.put(user.getUserName(),new Payable(transaction.getPayer(),transaction.getFractionalAmount())));

        return new Payables(payables);
    }


}
