package com.twu.biblioteca;

import com.twu.biblioteca.representations.User;

public class ViewUserDetailsService extends Service {

    private User user;
    private HomeService homeService;

    public ViewUserDetailsService(ServiceHandler serviceHandler, User user,
                                  HomeService homeService){
        super(user.getUsername(), serviceHandler);
        this.user = user;
        this.homeService = homeService;
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                serviceHandler.setService(homeService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            default:
                return ServiceHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        if(serviceHandler == null) {
            return;
        }
        serviceHandler.printHeader("DETAILS | " + user.getUsername());
        serviceHandler.printContent(user.getDetailsPrintFormat());
        serviceHandler.printContent(getOptionsPrintFormat());
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Back to Home Screen\n");
        return optionsPrintFormat.toString();
    }
}
