package com.twu.biblioteca;

import com.twu.biblioteca.exceptions.NoUserFoundException;
import com.twu.biblioteca.repositories.UserRepository;
import com.twu.biblioteca.representations.User;

public class UserLogInService extends Service{

    private static final String HEADER = "WELCOME TO BIBLIOTECA";
    private HomeService homeService;
    private UserRepository userRepository;

    public UserLogInService(ServiceHandler serviceHandler) {
        super(HEADER, serviceHandler);
        this.homeService = new HomeService(serviceHandler);
        this.userRepository = new UserRepository();
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "L":
                String username = serviceHandler.requestInput("Username");
                String password = serviceHandler.requestInput("Password");
                try {
                    User user = userRepository.login(username, password);
                    serviceHandler.setService(homeService);
                    return ServiceHandler.InputProcessResponse.SUCCESS;
                }
                catch (NoUserFoundException e) {
                    return ServiceHandler.InputProcessResponse.FAIL;
                }

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
