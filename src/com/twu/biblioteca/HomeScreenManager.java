package com.twu.biblioteca;

public class HomeScreenManager extends ServiceManager {

    private static final String HEADER = "HOME";
    private BookScreenManager bookScreenManager;

    public HomeScreenManager(UIHandler uiHandler) {
        super(HEADER, uiHandler);
        this.bookScreenManager = new BookScreenManager(this, uiHandler);
    }

    @Override
    public UIHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                uiHandler.setServiceManager(bookScreenManager);
                return UIHandler.InputProcessResponse.SUCCESS;
            case "M":
                System.out.println("DO MOVIES");
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
        return optionsPrintFormat.toString();
    }




}
