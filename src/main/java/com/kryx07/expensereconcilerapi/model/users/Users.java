package com.kryx07.expensereconcilerapi.model.users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Users implements Serializable {

    private long serialVersionUID = 13853924648436l;

    private Set<User> users = new HashSet<>();

    public Users(Set<User> users) {
        this.users = users;
    }

    public Users() {
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
        return true;
    }

    public boolean delete(String userName) {

        if (!users.contains(get(userName))) {
            return false;
        }
        users.remove(get(userName));
        return true;
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