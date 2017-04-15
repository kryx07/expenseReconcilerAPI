package com.kryx07.expensereconcilerapi.services;

import com.kryx07.expensereconcilerapi.utils.FileProcessor;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UsersService {

    private FileProcessor<Users> usersFileProcessor = new FileProcessor<>("users.o");

    public Users getAllUsers() {
        return usersFileProcessor.readAll();
    }

    public boolean addUser(User user) {

        Users users = usersFileProcessor.readAll();
        if (users == null) {
            users= new Users(new HashMap<String, User>());
        }


        boolean isAdded = users.add(user);
        usersFileProcessor.save(users);

        return isAdded;
    }

    /*public Users createTransactionsWithError(String errorMessage) {
        Users users = new Transactions(null);
        users.setErrorMessage(errorMessage);
        return users;
    }*/
}
