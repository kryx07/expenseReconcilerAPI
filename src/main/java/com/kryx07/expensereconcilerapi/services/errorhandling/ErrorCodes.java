package com.kryx07.expensereconcilerapi.services.errorhandling;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by wd40 on 21.04.17.
 */
public enum ErrorCodes {
    NO_TRANSACTIONS("There are no transactions!"),
    NO_TRANSACTION_WITH_ID("There is no transaction with the given id!"),
    NO_USERS("There are no users!");

    public String code;
    public String description;

    ErrorCodes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
