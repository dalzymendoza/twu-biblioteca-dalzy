package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableLibraryItemException;
import com.twu.biblioteca.errors.NonexistingLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;

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
        serviceHandler.printContent("Please type the ID of the library item you're returning: ");
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
