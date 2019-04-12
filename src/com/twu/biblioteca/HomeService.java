package com.twu.biblioteca;

public class HomeService extends Service {

    private static final String HEADER = "HOME";
    private BookLibraryService bookLibraryService;

    public HomeService(UIHandler uiHandler) {
        super(HEADER, uiHandler);
        this.bookLibraryService = new BookLibraryService(this, uiHandler);
    }

    @Override
    public UIHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                uiHandler.setService(bookLibraryService);
                return UIHandler.InputProcessResponse.SUCCESS;
            case "M":
                System.out.println("Coming soon!");
                return UIHandler.InputProcessResponse.SUCCESS;
            default:
                return UIHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        if(uiHandler == null) {
            return;
        }
        uiHandler.printHeader(HEADER);
        uiHandler.printContent(getOptionsPrintFormat());
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Books Library\n");
        optionsPrintFormat.append("[M] Movies Library\n");
        optionsPrintFormat.append("[Q] Quit application\n");
        return optionsPrintFormat.toString();
    }




}
