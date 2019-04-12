package com.twu.biblioteca;

public abstract class Service {

    String header;
    UIHandler uiHandler;

    public Service(String header, UIHandler uiHandler) {
        this.header = header;
        this.uiHandler = uiHandler;
    }

    public abstract UIHandler.InputProcessResponse processInput(String input);
    public abstract void displayStartScreen();

    public void setUIHandler(UIHandler uiHandler) {
        this.uiHandler = uiHandler;
    }
}
