package com.kryx07.expensereconcilerapi.controllers;

import com.kryx07.expensereconcilerapi.model.users.User;
import com.kryx07.expensereconcilerapi.model.users.Users;
import com.kryx07.expensereconcilerapi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Users> getAllUsers() {
        return new ResponseEntity<Users>(usersService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)//,consumes = "application/json")
    public ResponseEntity<User>   addUser(@RequestBody User user) {

        URI uri = null;
        try {
            uri = new URI(ServletUriComponentsBuilder.fromCurrentRequestUri().build() + "/" + user.getUserName());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return usersService.addUser(user)?
                ResponseEntity.created(uri).build() :
                ResponseEntity.badRequest().build();
    }
}
