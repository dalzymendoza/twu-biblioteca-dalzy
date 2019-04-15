package com.twu.biblioteca;

import com.twu.biblioteca.repositories.LibraryRepository;
import com.twu.biblioteca.representations.LibraryItem;

import java.util.List;

public class CheckedOutAdminService extends Service {

    private static final String HEADER = "CHECKED OUT ADMIN";
    private static final String NO_ITEMS_MESSAGE = "No checked out items at the moment.\n";

    private LibraryRepository libraryRepository;
    private LibraryService libraryService;
    private ServiceHandler serviceHandler;

    public CheckedOutAdminService(ServiceHandler serviceHandler, LibraryService libraryService,
                                  LibraryRepository libraryRepository) {

        super(HEADER, serviceHandler);
        this.serviceHandler = serviceHandler;
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
                return ServiceHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        serviceHandler.printHeader(HEADER);
        serviceHandler.printSubheader("Checked out items");
        serviceHandler.printContent(getCheckedOutLibraryItemsPrintFormat());
        serviceHandler.printContent(getOptonsPrintFormat());
    }

    private String getOptonsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Back to Library Screen\n");
        return optionsPrintFormat.toString();
    }

    private String getCheckedOutLibraryItemsPrintFormat() {
        List<LibraryItem> libraryItems = libraryRepository.viewAllLibraryItems();
        if(libraryItems.size() == 0) {
            return NO_ITEMS_MESSAGE;
        }
        StringBuilder allItemsPrintFormat = new StringBuilder();
        for (LibraryItem libraryItem : libraryItems){
            if (!libraryItem.getAvailability()) {
                allItemsPrintFormat.append(libraryItem.getAdminDetailsPrintFormat() + "\nL");
            }
        }
        return allItemsPrintFormat.toString();
    }
}
