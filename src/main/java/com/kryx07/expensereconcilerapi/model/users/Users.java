package com.kryx07.expensereconcilerapi.model.users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable{

    private long serialVersionUID = 13853924648436l;

    private Map<String,User> users = new HashMap<>();

    public Users(HashMap<String,User> users) {
        this.users = users;
    }

    public Users() {
    }

    public Map<String,User>  getUsers() {
        return users;
    }

    public void setUsers(HashMap<String,User>  users) {
        this.users = users;
    }

    public User get(String userName){
        return users.get(userName);
    }

    public boolean add(User user){
        if(users.containsKey(user.getUserName())){
            return false;
        }
        users.put(user.getUserName(),user);
        return true;
    }

    public boolean delete(String userName){
        if(!users.containsKey(userName)){
            return false;
        }
        users.remove(userName);
        return true;
    }

    public boolean contains(String userName){
        return users.containsKey(userName);
    }

    public boolean contains(User user){
        return users.containsValue(user);
    }

    public int size(){
        return users.size();
    }

   /* public boolean update(String userName, User user) {
        try {
            users.replace(userName, user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }*/

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
