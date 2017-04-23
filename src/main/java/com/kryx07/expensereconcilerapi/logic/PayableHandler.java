package com.kryx07.expensereconcilerapi.logic;

import com.kryx07.expensereconcilerapi.model.payables.Payable;
import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.transactions.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
                .forEach(user -> payables.add(new Payable(transaction.getPayer(),user,getFractionalAmount())));

        return payables;
    }

    private BigDecimal getFractionalAmount() {

        return transaction.isCommon() ?
                transaction.getAmount()
                        .divide(BigDecimal.valueOf(transaction.getTransactionParties().size()),10, RoundingMode.CEILING):
                transaction.getAmount()
                        .divide(BigDecimal.valueOf(transaction.getTransactionParties().size() - 1),10, RoundingMode.CEILING);
    }


}
