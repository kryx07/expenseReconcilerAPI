package com.kryx07.expensereconcilerapi.model.payables;


import com.kryx07.expensereconcilerapi.model.users.User;

import java.math.BigDecimal;

public class Payable {

    User user;
    User payer;
    BigDecimal amount;
}
