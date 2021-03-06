package com.twu.biblioteca;

import com.twu.biblioteca.repositories.SampleBookLibraryRepository;
import com.twu.biblioteca.repositories.SampleMovieLibraryRepository;
import com.twu.biblioteca.representations.User;

public class HomeService extends Service {

    private static final String HEADER = "HOME";
    private LibraryService bookLibraryService;
    private LibraryService movieLibraryService;
    private UserLogInService userLogInService;

    public HomeService(ServiceHandler serviceHandler, UserLogInService userLogInService) {
        super(HEADER, serviceHandler);
        this.bookLibraryService = new LibraryService("BOOKS",this, serviceHandler, new SampleBookLibraryRepository());
        this.movieLibraryService = new LibraryService("MOVIES",this, serviceHandler, new SampleMovieLibraryRepository());
        this.userLogInService = userLogInService;
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "BO":
                serviceHandler.setService(bookLibraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "M":
                serviceHandler.setService(movieLibraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "B":
                serviceHandler.setService(userLogInService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "I":
                User.UserPermissions userPermissions = serviceHandler.getCurrentUserPermissions();
                if (userPermissions == User.UserPermissions.CUSTOMER) {
                    serviceHandler.setService(new ViewUserDetailsService(serviceHandler,
                            serviceHandler.getCurrentUser(), this));
                    return ServiceHandler.InputProcessResponse.SUCCESS;
                }
                return ServiceHandler.InputProcessResponse.FAIL;
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
        optionsPrintFormat.append("[BO] Books Library\n");
        optionsPrintFormat.append("[M] Movies Library\n");
        User.UserPermissions userPermissions = serviceHandler.getCurrentUserPermissions();
        if (userPermissions == User.UserPermissions.CUSTOMER) {
            optionsPrintFormat.append("[I] View My Information\n");
        }
        optionsPrintFormat.append("[B] Back to Login Screen\n");
        optionsPrintFormat.append("[Q] Quit application\n");
        return optionsPrintFormat.toString();
    }




}
