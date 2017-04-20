package com.kryx07.expensereconcilerapi.controllers;

import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.services.ReconciliationService;
import com.kryx07.expensereconcilerapi.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by wd40 on 16.04.17.
 */
@RestController
@RequestMapping("/reconciliation")
public class ReconciliationController {

    private final ReconciliationService reconciliationService;

    @Autowired
    public ReconciliationController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Set<Users>> getReconcilingParties() {
        return new ResponseEntity<Set<Users>>(reconciliationService.getReconcilingParties(), HttpStatus.OK);
    }

    @RequestMapping(value = "/selected", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Payables> getPayablesByReconcilingParties(@RequestBody Users reconcilingParties) {
        return new ResponseEntity<Payables>(reconciliationService.getPayablesByReconcilingParties(reconcilingParties),HttpStatus.OK);
    }
}
