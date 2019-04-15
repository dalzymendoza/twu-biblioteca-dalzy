package com.twu.biblioteca;

import com.twu.biblioteca.exceptions.NonexistingLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;
import com.twu.biblioteca.representations.LibraryItem;
import com.twu.biblioteca.representations.User;

import java.util.List;

public class LibraryService extends Service {

    public static final String NO_ITEMS_MESSAGE = "Sorry, we don't have any library items at the moment.\n";

    private LibraryRepository libraryRepository;
    private HomeService homeService;
    private ReturnLibraryItemService returnLibraryItemService;

    public LibraryService(String header, HomeService homeService, ServiceHandler serviceHandler,
                          LibraryRepository libraryRepository) {
        super(header, serviceHandler);
        this.homeService = homeService;
        this.libraryRepository = libraryRepository;
        this.returnLibraryItemService = new ReturnLibraryItemService(serviceHandler, this, this.libraryRepository);
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch (input) {
            case "B":
                serviceHandler.setService(homeService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "R":
                serviceHandler.setService(returnLibraryItemService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "C":
                User.UserPermissions userPermissions = serviceHandler.getCurrentUserPermissions();
                if (userPermissions == User.UserPermissions.LIBRARIAN) {
                    serviceHandler.setService(new CheckedOutAdminService(serviceHandler, this,
                            libraryRepository));
                    return ServiceHandler.InputProcessResponse.SUCCESS;
                }
                return ServiceHandler.InputProcessResponse.FAIL;
            default:
                try {
                    int bookId = Integer.parseInt(input);
                    serviceHandler.setService(new ViewLibraryItemService(serviceHandler, this,
                            libraryRepository, libraryRepository.getLibraryItem(bookId)));
                    return ServiceHandler.InputProcessResponse.SUCCESS;
                }
                catch (NumberFormatException | NonexistingLibraryItemException e) {
                    return ServiceHandler.InputProcessResponse.FAIL;
                }
        }
    }

    @Override
    public void displayStartScreen() {
        if(serviceHandler == null) {
            return;
        }
        serviceHandler.printHeader(header);
        serviceHandler.printContent(getAvailableLibraryItemsPrintFormat());
        serviceHandler.printContent(getOptionsPrintFormat());
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Back to Home Screen\n");
        optionsPrintFormat.append("[R] Return a Library Item\n");
        User.UserPermissions userPermissions = serviceHandler.getCurrentUserPermissions();
        if (userPermissions == User.UserPermissions.LIBRARIAN) {
            optionsPrintFormat.append("[C] View Checked Out Items\n");
        }
        return optionsPrintFormat.toString();
    }

    private String getAvailableLibraryItemsPrintFormat() {
        List<LibraryItem> libraryItems = libraryRepository.viewAllLibraryItems();
        if(libraryItems.size() == 0) {
            return NO_ITEMS_MESSAGE;
        }
        StringBuilder allItemsPrintFormat = new StringBuilder("Available items: \n");
        for (LibraryItem libraryItem : libraryItems){
            if (libraryItem.getAvailability()) {
                allItemsPrintFormat.append(libraryItem.getLibraryItemOptionPrintFormat() + "\n");
            }
        }
        return allItemsPrintFormat.toString();
    }
}
