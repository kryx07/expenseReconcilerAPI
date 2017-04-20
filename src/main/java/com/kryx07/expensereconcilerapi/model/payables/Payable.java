package com.kryx07.expensereconcilerapi.model.payables;


import com.kryx07.expensereconcilerapi.model.users.User;

import java.io.Serializable;
import java.math.BigDecimal;

public class Payable implements Serializable {

    private long serialVersionUID = 13842227326528l;
    private User payer;
    private BigDecimal amount;

    public Payable() {
    }

    public Payable(User payer, BigDecimal amount) {

        this.payer = payer;
        this.amount = amount;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
