package com.twu.biblioteca;

import com.twu.biblioteca.exceptions.NonexistingLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;
import com.twu.biblioteca.representations.LibraryItem;

import java.util.List;

public class LibraryService extends Service {

    public static final String NO_BOOKS_MESSAGE = "Sorry, we don't have any library items at the moment.\n";

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
                System.out.println("Return Service");
                serviceHandler.setService(returnLibraryItemService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
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
        serviceHandler.printContent(getAllBooksPrintFormat());
        serviceHandler.printContent(getOptionsPrintFormat());
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Back to Home Screen\n");
        optionsPrintFormat.append("[R] Return a Library Item\n");
        return optionsPrintFormat.toString();
    }

    private String getAllBooksPrintFormat() {
        List<LibraryItem> libraryItems = libraryRepository.viewAllLibraryItems();
        if(libraryItems.size() == 0) {
            return NO_BOOKS_MESSAGE;
        }
        StringBuilder allBooksPrintFormat = new StringBuilder();
        for (LibraryItem libraryItem : libraryItems){
            if (libraryItem.getAvailability()) {
                allBooksPrintFormat.append(libraryItem.getLibraryItemOptionPrintFormat() + "\n");
            }
        }
        return allBooksPrintFormat.toString();
    }
}
