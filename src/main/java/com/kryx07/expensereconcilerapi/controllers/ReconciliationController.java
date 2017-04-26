package com.kryx07.expensereconcilerapi.controllers;

import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.users.UserGroups;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.services.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserGroups> getAllReconcilingUserGroups() {
        return new ResponseEntity<UserGroups>(reconciliationService.getAllReconcilingUserGroups(), HttpStatus.OK);
    }

    @RequestMapping(value = "/selected", method = RequestMethod.GET)
    public ResponseEntity<Users> getReconcilingPartiesById(@RequestParam(value = "id",required = true) String id) {
        return new ResponseEntity<Users>(reconciliationService.getReconcilingUsersByGroupId(id),HttpStatus.OK);
    }

    @RequestMapping(value = "/payables", method = RequestMethod.GET)
    public ResponseEntity<Payables> getPayablesByReconcilingParties(@RequestParam(value = "id",required = true) String id) {
        return new ResponseEntity<Payables>(
                reconciliationService
                        .getPayablesByReconcilingUsers(
                                reconciliationService
                                        .getReconcilingUsersByGroupId(id)),HttpStatus.OK);
    }


}
