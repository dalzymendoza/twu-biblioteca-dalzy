package com.twu.biblioteca;

import com.twu.biblioteca.representations.User;

import java.util.Scanner;

public class ServiceHandler {
    private static final int HEADER_WIDTH = 50;
    private static final String RESPONSE_TO_USER_ACTION_BULLET = "*********";

    public enum InputProcessResponse {
        SUCCESS, FAIL
    }

    private User currentUser;
    private Service service;
    private Scanner scanner;

    public ServiceHandler() {
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
    }

    public ServiceHandler(Service service) {
        this.service = service;
        this.currentUser = null;
        this.scanner = new Scanner(System.in);
    }

    public void runCurrentService() {
        if (service == null) {
            System.out.println("Sorry, no available service");
            return;
        }
        service.displayStartScreen();
        String optionString = scanner.nextLine();
        while(!optionString.equals("Q")) {
            InputProcessResponse response = service.processInput(optionString);
            switch(response) {
                case SUCCESS:
                    break;
                case FAIL:
                    printUserActionRespone("Invalid input");
                    break;
            }
            service.displayStartScreen();
            optionString = scanner.nextLine();
        }
        quit();
    }

    public void quit() {
        scanner.close();
    }

    public String requestInput(String inputDescription) {
        System.out.println(inputDescription + ": ");
        String input = scanner.nextLine();
        return input;
    }

    public void printContent(String content) {
        System.out.println(content);
    }

    public void printHeader(String headerTitle){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < HEADER_WIDTH; i++) {
            builder.append("=");
        }
        builder.append("\n" + headerTitle + "\n");
        for (int i = 0; i < HEADER_WIDTH; i++) {
            builder.append("=");
        }
        builder.append("\n");
        System.out.print(builder);
    }

    public void printUserActionRespone (String content) {
        System.out.println(RESPONSE_TO_USER_ACTION_BULLET + content + RESPONSE_TO_USER_ACTION_BULLET + "\n");
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User.UserPermissions getCurrentUserPermissions() {
        if (currentUser != null) {
            return currentUser.getUserPermissions();
        }
        return User.UserPermissions.NON_CUSTOMER;
    }
}
