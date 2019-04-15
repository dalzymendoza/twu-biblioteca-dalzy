package com.twu.biblioteca;

import com.twu.biblioteca.repositories.LibraryRepository;
import com.twu.biblioteca.representations.LibraryItem;

public class ViewLibraryItemService extends Service {

    private LibraryItem libraryItem;
    private LibraryService libraryService;
    private CheckoutLibraryItemService checkoutLibraryItemService;
    private ServiceHandler serviceHandler;

    public ViewLibraryItemService(ServiceHandler serviceHandler, LibraryService libraryService,
                                  LibraryRepository libraryRepository, LibraryItem libraryItem) {
        super(libraryItem.getTitle(), serviceHandler);
        this.serviceHandler = serviceHandler;
        this.libraryItem = libraryItem;
        this.libraryService = libraryService;
        this.checkoutLibraryItemService = new CheckoutLibraryItemService(serviceHandler, libraryService,
                                        this, libraryRepository);
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                serviceHandler.setService(libraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "C":
                serviceHandler.setService(checkoutLibraryItemService);
                checkoutLibraryItemService.setLibraryItem(libraryItem);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            default:
                return ServiceHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        serviceHandler.printHeader(libraryItem.getTitle());
        serviceHandler.printContent(libraryItem.getExtraDetailsPrintFormat());
        serviceHandler.printContent(getOptonsPrintFormat());
    }

    private String getOptonsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[C] Checkout this library item\n");
        optionsPrintFormat.append("[B] Back to Library Screen\n");
        return optionsPrintFormat.toString();
    }

}
