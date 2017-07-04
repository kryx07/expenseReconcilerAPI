package com.kryx07.expensereconcilerapi.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "USERS_GROUPS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users implements Serializable {

    @Transient
    private long serialVersionUID = 13853924648436l;


    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private String id;

    @OneToMany(mappedBy = "userName")
    private Set<User> users = new HashSet<>();

    @Transient
    @ApiModelProperty(hidden = true)
    private String errorMessage;

    public Users(Set<User> users) {
        this.users = users;
    }

    public Users() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public User get(String userName) {
        return users
                .stream()
                .filter(u -> u.getUserName()
                        .equals(userName))
                .findFirst()
                .orElse(null);
    }

    public boolean add(User user) {
        if (users.contains(user)) {
            return false;
        }
        users.add(user);
        return users.contains(user);
    }

    public void addAll(Users users) {
        users.getUsers().stream().forEach(u -> add(u));

    }

    public boolean delete(String userName) {

        if (!users.contains(get(userName))) {
            return false;
        }
        users.remove(get(userName));
        return !users.contains(userName);
    }

    public boolean delete(User user) {

        if (!users.contains(user)) {
            return false;
        }
        users.remove(user);
        return !users.contains(user);
    }

    public boolean contains(String userName) {
        return users.contains(get(userName));
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    public int size() {
        return users.size();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return users == null;
    }

    public void distinct() {
        users = users.stream().distinct().collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users1 = (Users) o;

        if (serialVersionUID != users1.serialVersionUID) return false;
        return users != null ? users.equals(users1.users) : users1.users == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (serialVersionUID ^ (serialVersionUID >>> 32));
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}