package com.kryx07.expensereconcilerapi.model.reconciliations;

import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.users.Users;

/**
 * Created by wd40 on 16.04.17.
 */
public class Reconciliation {

    private Users reconcilingParties;
    private Payables payables;

    public Reconciliation(Users reconcilingParties, Payables payables) {
        this.reconcilingParties = reconcilingParties;
        this.payables = payables;
    }

    public Reconciliation() {
    }

    public Payables getPayables() {
        return payables;
    }

    public void setPayables(Payables payables) {
        this.payables = payables;
    }

    public Users getReconcilingParties() {
        return reconcilingParties;
    }

    public void setReconcilingParties(Users reconcilingParties) {
        this.reconcilingParties = reconcilingParties;
    }
}
