package com.kryx07.expensereconcilerapi.model.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGroups implements Serializable {

    private long serialVersionUID = 83863224444486L;

    private Set<Users> userGroups = new HashSet<>();

    @ApiModelProperty(hidden = true)
    private String errorMessage;

    public Users get(String id){
        return userGroups
                .stream()
                .filter(users -> users.getId().equals(id))
                .findFirst()
                .orElse(new Users());
    }

    public Set<Users> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<Users> userGroups) {
        this.userGroups = userGroups;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean contains(Users users) {
        return userGroups.contains(users);
    }

    public boolean add(Users users) {
        if (userGroups.contains(users)) {
            return false;
        }
        users.setId(UUID.randomUUID().toString());
        userGroups.add(users);
        return userGroups.contains(users);
    }
}