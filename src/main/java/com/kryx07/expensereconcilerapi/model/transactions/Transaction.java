package com.kryx07.expensereconcilerapi.model.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryx07.expensereconcilerapi.model.payables.Payables;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction implements Serializable {

    private String id;

    private long serialVersionUID = 83843247273768l;
    private LocalDate date;
    private BigDecimal amount;
    private String description;

    private boolean common;
    //@ApiModelProperty(hidden = true)

    private User payer;

    //@ApiModelProperty(hidden = true)

    private Users transactionParties;
    @ApiModelProperty(hidden = true)
    private Payables payables;
    @ApiModelProperty(hidden = true)
    private String errorMessage;

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }
    @JsonIgnore
    public Users getTransactionParties() {
        return Optional.ofNullable(transactionParties)
                .orElse(new Users());
    }

    @JsonProperty("users")
    public void setTransactionParties(Users transactionParties) {
        this.transactionParties = transactionParties;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public Payables getPayables() {
        return payables;
    }

    public void setPayables(Payables payables) {
        this.payables = payables;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
