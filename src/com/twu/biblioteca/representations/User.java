package com.twu.biblioteca.representations;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.validator.routines.EmailValidator;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Phonenumber.PhoneNumber phoneNumber;
    private UserPermissions userPermissions;

    private static final String PHONE_REGION = "IN";

    public enum UserPermissions {
        CUSTOMER, LIBRARIAN, NON_CUSTOMER
    }


    public User(String username, String password, String firstName, String lastName, String email, String phoneNumber)
            throws IllegalArgumentException, NumberParseException {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = PhoneNumberUtil.getInstance().parse(phoneNumber, PHONE_REGION);
        if (isValidEmail(email)) {
            this.email = email;
        }
        else {
            throw new IllegalArgumentException();
        }
        this.userPermissions = UserPermissions.CUSTOMER;
    }


    public User(String username, String password, String firstName, String lastName, String email,
                String phoneNumber, UserPermissions userPermissions)
            throws IllegalArgumentException, NumberParseException{
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = PhoneNumberUtil.getInstance().parse(phoneNumber, PHONE_REGION);
        if (isValidEmail(email)) {
            this.email = email;
        }
        else {
            throw new IllegalArgumentException();
        }
        this.userPermissions = userPermissions;
    }

    public boolean validatePassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public String getDetailsPrintFormat() {
        StringBuilder details = new StringBuilder();
        details.append("First Name: " + firstName + "\n");
        details.append("Last Name: " + lastName + "\n");
        details.append("Email: " + email + "\n");
        details.append("Phone Number: " + phoneNumber.getCountryCode() +
                phoneNumber.getNationalNumber() + "\n");
        return details.toString();
    }

    public String getUsername() {
        return username;
    }

    public UserPermissions getUserPermissions() {
        return userPermissions;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
