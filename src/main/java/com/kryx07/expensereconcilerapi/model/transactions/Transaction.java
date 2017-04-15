package com.kryx07.expensereconcilerapi.model.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction implements Serializable {

    private long serialVersionUID = 83843247273768l;


    private String id;
    private LocalDate addDate;
    private User payer;
    private String description;
    @ApiModelProperty(hidden = true)
//    @JsonProperty(value = "errorMessage", access = JsonProperty.Access.READ_ONLY)
    private String errorMessage;

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private BigDecimal amount;

    private Users reconcilingUsers;

    public LocalDate getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Users getReconcilingUsers() {
        return reconcilingUsers;
    }

    public void setReconcilingUsers(Users reconcilingUsers) {
        this.reconcilingUsers = reconcilingUsers;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //@JsonProperty
    public String getErrorMessage() {
        return errorMessage;
    }

    //@JsonIgnore
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (serialVersionUID != that.serialVersionUID) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (addDate != null ? !addDate.equals(that.addDate) : that.addDate != null) return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return reconcilingUsers != null ? reconcilingUsers.equals(that.reconcilingUsers) : that.reconcilingUsers == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (serialVersionUID ^ (serialVersionUID >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (addDate != null ? addDate.hashCode() : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (reconcilingUsers != null ? reconcilingUsers.hashCode() : 0);
        return result;
    }
}
