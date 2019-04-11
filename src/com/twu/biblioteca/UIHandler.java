package com.twu.biblioteca;

import java.util.Scanner;

public class UIHandler {
    private static final int HEADER_WIDTH = 50;
    private static final String RESPONSE_TO_USER_ACTION_BULLET = "*********";

    public enum InputProcessResponse {
        SUCCESS, FAIL
    }

    private ServiceManager serviceManager;
    private Scanner scanner;

    public UIHandler() {
        this.scanner = new Scanner(System.in);
    }

    public UIHandler(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
        this.scanner = new Scanner(System.in);
    }

    public void runCurrentService() {
        if (serviceManager == null) {
            System.out.println("Sorry, no available service");
            return;
        }
        serviceManager.displayStartScreen();
        String optionString = scanner.next();
        while(!optionString.equals("Q")) {
            InputProcessResponse response = serviceManager.processInput(optionString);
            switch(response) {
                case SUCCESS:
                    break;
                case FAIL:
                    printUserActionRespone("Invalid input");
                    break;
            }
            serviceManager.displayStartScreen();
            optionString = scanner.next();
        }
        quit();
    }

    public void quit() {
        scanner.close();
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

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
}
