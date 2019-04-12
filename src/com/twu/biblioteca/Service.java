package com.twu.biblioteca;

public abstract class Service {

    String header;
    ServiceHandler serviceHandler;

    public Service(String header, ServiceHandler serviceHandler) {
        this.header = header;
        this.serviceHandler = serviceHandler;
    }

    public abstract ServiceHandler.InputProcessResponse processInput(String input);
    public abstract void displayStartScreen();

    public void setUIHandler(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }
}
