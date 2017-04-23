package com.kryx07.expensereconcilerapi.model.payables;


import com.kryx07.expensereconcilerapi.model.users.User;

import java.io.Serializable;
import java.math.BigDecimal;

public class Payable implements Serializable {

    private long serialVersionUID = 13842227326528l;

    private User payer;
    private User debtor;
    private BigDecimal amount;

    public Payable() {
    }

    public Payable(User payer, User debtor, BigDecimal amount) {
        this.payer = payer;
        this.debtor = debtor;
        this.amount = amount;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public User getDebtor() {
        return debtor;
    }

    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
