package com.twu.biblioteca.repositories;

import com.twu.biblioteca.exceptions.NoUserFoundException;
import com.twu.biblioteca.representations.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private Map<String, User> userMap;

    public UserRepository() {
        userMap = new HashMap<>();
        userMap.put("spider-man", new User("spider-man", "spider-man"));
        userMap.put("gwen", new User("gwen", "gwen"));
        userMap.put("peniparker", new User("peniparker", "peniparker"));
    }

    public User login(String username, String password) throws NoUserFoundException{
        if (userMap.containsKey(username) && userMap.get(username).validatePassowrd(password)) {
            return userMap.get(username);
        }
        else {
            throw new NoUserFoundException();
        }
    }
}
