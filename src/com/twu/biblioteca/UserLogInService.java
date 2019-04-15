package com.twu.biblioteca;

public class UserLogInService extends Service{

    private static final String HEADER = "LOG IN";
    private HomeService homeService;

    public UserLogInService(ServiceHandler serviceHandler) {
        super(HEADER, serviceHandler);
        this.homeService = new HomeService(serviceHandler);
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "L":
                String username = serviceHandler.requestInput("Username");
                String password = serviceHandler.requestInput("Password");

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
        serviceHandler.printHeader(HEADER);
        serviceHandler.printContent(getOptionsPrintFormat());
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[L] Login\n");
        optionsPrintFormat.append("[Q] Quit application\n");
        return optionsPrintFormat.toString();
    }
}
