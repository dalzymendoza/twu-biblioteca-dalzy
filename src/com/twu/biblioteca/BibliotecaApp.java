package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String args[]) {
        ServiceHandler serviceHandler = new ServiceHandler();
        HomeService homeServiceManager = new HomeService(serviceHandler);
        serviceHandler.setService(homeServiceManager);
        serviceHandler.runCurrentService();
    }
}

