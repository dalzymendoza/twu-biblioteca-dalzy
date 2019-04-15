package com.twu.biblioteca;

import com.twu.biblioteca.repositories.SampleBookLibraryRepository;
import com.twu.biblioteca.repositories.SampleMovieLibraryRepository;

public class HomeService extends Service {

    private static final String HEADER = "HOME";
    private LibraryService bookLibraryService;
    private LibraryService movieLibraryService;

    public HomeService(ServiceHandler serviceHandler) {
        super(HEADER, serviceHandler);
        this.bookLibraryService = new LibraryService("BOOKS",this, serviceHandler, new SampleBookLibraryRepository());
        this.movieLibraryService = new LibraryService("MOVIES",this, serviceHandler, new SampleMovieLibraryRepository());

    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                serviceHandler.setService(bookLibraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "M":
                serviceHandler.setService(movieLibraryService);
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
        optionsPrintFormat.append("[B] Books Library\n");
        optionsPrintFormat.append("[M] Movies Library\n");
        optionsPrintFormat.append("[Q] Quit application\n");
        return optionsPrintFormat.toString();
    }




}
