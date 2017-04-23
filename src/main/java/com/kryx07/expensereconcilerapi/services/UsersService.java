package com.kryx07.expensereconcilerapi.services;

import com.kryx07.expensereconcilerapi.model.transactions.Transaction;
import com.kryx07.expensereconcilerapi.utils.FileProcessor;
import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static com.kryx07.expensereconcilerapi.services.errorhandling.ErrorCodes.NO_USERS;

@Service
public class UsersService {

    private FileProcessor<Users> usersFileProcessor = new FileProcessor<>("users.o");

    public Users getAllUsers() {
        return Optional.ofNullable(usersFileProcessor.readAll()).orElse(createUsersWithError(NO_USERS.toString()));
    }

    public boolean addUser(User user) {

        Users users = usersFileProcessor.readAll();
        if (users == null) {
            users= new Users( new HashSet<User>());
        }


        boolean isAdded = users.add(user);
        usersFileProcessor.save(users);

        return isAdded;
    }

    public User createUserWithError(String errorMessage) {
        User user = new User();
        user.setErrorMessage(errorMessage);
        return user;
    }

    public Users createUsersWithError(String errorMessage) {
        Users users = new Users(null);
        users.setErrorMessage(errorMessage);
        return users;
    }
}
