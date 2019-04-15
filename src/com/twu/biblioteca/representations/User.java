package com.twu.biblioteca.representations;

public class User {
    private String username;
    private String password;
    private UserPermissions userPermissions;

    public enum UserPermissions {
        CUSTOMER, LIBRARIAN, NON_CUSTOMER
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        userPermissions = UserPermissions.CUSTOMER;
    }

    public User(String username, String password, UserPermissions userPermissions) {
        this.username = username;
        this.password = password;
        this.userPermissions = userPermissions;
    }

    public boolean validatePassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    public UserPermissions getUserPermissions() {
        return userPermissions;
    }

}
