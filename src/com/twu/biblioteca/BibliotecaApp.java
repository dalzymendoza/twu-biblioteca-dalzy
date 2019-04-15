package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String args[]) {
        ServiceHandler serviceHandler = new ServiceHandler();
        UserLogInService userLogInService = new UserLogInService(serviceHandler);
        serviceHandler.setService(userLogInService);
        serviceHandler.runCurrentService();
    }
}

