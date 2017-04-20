package com.kryx07.expensereconcilerapi.services;

import com.kryx07.expensereconcilerapi.logic.Reconciler;
import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.users.Users;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReconciliationService {

    Reconciler reconciler = new Reconciler();

    public Set<Users> getReconcilingParties(){
        return reconciler.getReconciliationParties();
    }

    public Payables getPayablesByReconcilingParties(Users users){
        return reconciler.getPayablesByReconcilingParties(users);
    }
}
