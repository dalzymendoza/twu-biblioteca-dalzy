package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String args[]) {
        UIHandler uiHandler = new UIHandler();
        HomeScreenManager homeScreenManager = new HomeScreenManager(uiHandler);
        uiHandler.setServiceManager(homeScreenManager);
        uiHandler.runCurrentService();
    }
}
