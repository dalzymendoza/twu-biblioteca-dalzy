package com.twu.biblioteca.repositories;

import com.google.i18n.phonenumbers.NumberParseException;
import com.twu.biblioteca.exceptions.NoUserFoundException;
import com.twu.biblioteca.representations.User;

import javax.mail.internet.AddressException;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private Map<String, User> userMap;

    public UserRepository()  {
        userMap = new HashMap<>();
        try {
            userMap.put("spider-man", new User("spider-man", "spider-man",
                    "Peter", "Parker", "spiderman@marvel.com",
                    "+910123456789", User.UserPermissions.CUSTOMER));
            userMap.put("gwen", new User("gwen", "gwen",
                    "Gwen", "Stacy", "gwen@marvel.com", "+910123456788",
                    User.UserPermissions.CUSTOMER));
            userMap.put("peniparker", new User("peniparker", "peniparker",
                    "Peni", "Parker", "peniparker@marvel.com",
                    "+910123456787", User.UserPermissions.CUSTOMER));
            userMap.put("librarian", new User("librarian", "librarian",
                    "Miles", "Morales", "miles@marvel.com",
                    "+910123456786", User.UserPermissions.LIBRARIAN));
        }
        catch (IllegalArgumentException | NumberParseException e) {

        }
    }

    public User login(String username, String password) throws NoUserFoundException{
        if (userMap.containsKey(username) && userMap.get(username).validatePassword(password)) {
            return userMap.get(username);
        }
        else {
            throw new NoUserFoundException();
        }
    }
}
