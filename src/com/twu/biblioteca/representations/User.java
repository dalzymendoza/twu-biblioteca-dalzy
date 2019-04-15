package com.twu.biblioteca.representations;

public class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validatePassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

}
