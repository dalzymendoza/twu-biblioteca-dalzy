package com.twu.biblioteca;

import com.twu.biblioteca.exceptions.AvailableLibraryItemException;
import com.twu.biblioteca.exceptions.NonexistingLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;
import com.twu.biblioteca.representations.User;

public class ReturnLibraryItemService extends Service {

    public static final String HEADER = "RETURN SERVICE";

    public static final String SUCCESS = "Thank you! Library item has been successfully returned!";
    public static final String NON_EXISTING_LIBRARY_ITEM = "Sorry, there is no such library item in our system.";
    public static final String ALREADY_AVAILABLE_LIBRARY_ITEM = "That library item is not checked out.";

    private LibraryRepository libraryRepository;
    private LibraryService libraryService;


    public ReturnLibraryItemService(ServiceHandler serviceHandler, LibraryService libraryService, LibraryRepository libraryRepository) {
        super(HEADER, serviceHandler);
        this.libraryService = libraryService;
        this.libraryRepository = libraryRepository;
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                serviceHandler.setService(libraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            default:
                if (serviceHandler.getCurrentUserPermissions() == User.UserPermissions.NON_CUSTOMER) {
                    return ServiceHandler.InputProcessResponse.FAIL;
                }
                try {
                    int bookId = Integer.parseInt(input);
                    returnBook(bookId);
                    return ServiceHandler.InputProcessResponse.SUCCESS;
                }
                catch (NumberFormatException e) {
                    return ServiceHandler.InputProcessResponse.FAIL;
                }
        }
    }

    @Override
    public void displayStartScreen() {
        serviceHandler.printHeader(HEADER);
        serviceHandler.printContent(getOptionsPrintFormat());
        User.UserPermissions userPermissions = serviceHandler.getCurrentUserPermissions();
        switch (userPermissions) {
            case NON_CUSTOMER:
                serviceHandler.printContent("Returning is only allowed for registered library users.");
                break;
            case CUSTOMER:
            case LIBRARIAN:
                serviceHandler.printContent("Please type the ID of the library item you're returning: ");
                break;
        }
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Back to Library screen\n");
        return optionsPrintFormat.toString();
    }

    public void returnBook(int id) {
        try {
            libraryRepository.returnLibraryItem(id);
            serviceHandler.printUserActionRespone(SUCCESS);
        }
        catch (NonexistingLibraryItemException e) {
            serviceHandler.printUserActionRespone(NON_EXISTING_LIBRARY_ITEM);
        }
        catch (AvailableLibraryItemException e) {
            serviceHandler.printUserActionRespone(ALREADY_AVAILABLE_LIBRARY_ITEM);
        }
    }
}
