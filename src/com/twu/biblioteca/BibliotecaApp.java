package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String args[]) {
        UIHandler uiHandler = new UIHandler();
        HomeService homeServiceManager = new HomeService(uiHandler);
        uiHandler.setService(homeServiceManager);
        uiHandler.runCurrentService();
    }
}

