package com.twu.biblioteca.representations;

public class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validatePassowrd(String password) {
        if (this.password == password) {
            return true;
        }
        return false;
    }

}
