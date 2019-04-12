package com.twu.biblioteca;

public class HomeService extends Service {

    private static final String HEADER = "HOME";
    private BookLibraryService bookLibraryService;

    public HomeService(ServiceHandler serviceHandler) {
        super(HEADER, serviceHandler);
        this.bookLibraryService = new BookLibraryService(this, serviceHandler);
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                serviceHandler.setService(bookLibraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "M":
                System.out.println("Coming soon!");
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
