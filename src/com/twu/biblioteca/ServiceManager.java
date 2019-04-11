package com.twu.biblioteca;

public abstract class ServiceManager {

    String header;
    UIHandler uiHandler;

    public ServiceManager(String header, UIHandler uiHandler) {
        this.header = header;
        this.uiHandler = uiHandler;
    }

    public abstract UIHandler.InputProcessResponse processInput(String input);
    public abstract void displayStartScreen();

    public void setUIHandler(UIHandler uiHandler) {
        this.uiHandler = uiHandler;
    }
}
