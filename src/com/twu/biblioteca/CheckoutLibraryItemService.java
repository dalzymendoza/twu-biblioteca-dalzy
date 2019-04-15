package com.twu.biblioteca;

import com.twu.biblioteca.exceptions.NonexistingLibraryItemException;
import com.twu.biblioteca.exceptions.UnavailableLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;
import com.twu.biblioteca.representations.LibraryItem;


public class CheckoutLibraryItemService extends Service {

    public static final String NOT_AVAILABLE_LIBRARY_ITEM = "Sorry, that library item is not available.";
    public static final String NONEXISTING_LIBRARY_ITEM = "Sorry, this library item does not exist.";
    public static final String SUCCESS = "Thank you! Enjoy the library item.";

    public static final String HEADER = "CHECKOUT SERVICE";

    private LibraryRepository libraryRepository;
    private ViewLibraryItemService viewLibraryItemService;
    private LibraryService libraryService;
    private ServiceHandler serviceHandler;
    private LibraryItem libraryItem;

    public CheckoutLibraryItemService(ServiceHandler serviceHandler, LibraryService libraryService,
                                      ViewLibraryItemService viewLibraryItemService, LibraryRepository libraryRepository) {
        super(HEADER, serviceHandler);
        this.serviceHandler = serviceHandler;
        this.viewLibraryItemService = viewLibraryItemService;
        this.libraryRepository = libraryRepository;
        this.libraryService = libraryService;
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "Y":
                checkoutLibraryItem(libraryItem.getId());
                serviceHandler.setService(libraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "N":
                serviceHandler.setService(viewLibraryItemService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            default:
                return ServiceHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        if (libraryItem == null) {
            serviceHandler.printUserActionRespone("No library item selected for checkout");
            serviceHandler.setService(viewLibraryItemService);
        }
        else {
            serviceHandler.printHeader("Checking out " + libraryItem.getTitle());
            serviceHandler.printContent(getOptionsPrintFormat());
        }

    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[Y] Yes, check out.\n");
        optionsPrintFormat.append("[N] No. \n");
        return optionsPrintFormat.toString();
    }

    public void checkoutLibraryItem(int id) {
        try {
            libraryRepository.checkoutLibraryItem(id);
            serviceHandler.printUserActionRespone(SUCCESS);
        }
        catch (UnavailableLibraryItemException e) {
            serviceHandler.printUserActionRespone(NOT_AVAILABLE_LIBRARY_ITEM);
        }
        catch (NonexistingLibraryItemException e) {
            serviceHandler.printUserActionRespone(NONEXISTING_LIBRARY_ITEM);
        }
    }

    public void setLibraryItem(LibraryItem libraryItem) {
        this.libraryItem = libraryItem;
    }
}
