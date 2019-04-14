package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableLibraryItemError;
import com.twu.biblioteca.errors.NonexistingLibraryItemError;
import com.twu.biblioteca.repositories.BookRepository;

public class ReturnService extends Service {

    public static final String HEADER = "RETURN SERVICE";

    public static final String SUCCESS = "Thank you! Book has been successfully returned!";
    public static final String NON_EXISTING_BOOK = "Sorry, there is no such book in our system.";
    public static final String ALREADY_AVAILABLE = "That book is not checked out.";

    private BookRepository bookRepository;
    private BookLibraryService bookLibraryService;


    public ReturnService(ServiceHandler serviceHandler, BookLibraryService bookLibraryService, BookRepository bookRepository) {
        super(HEADER, serviceHandler);
        this.bookLibraryService = bookLibraryService;
        this.bookRepository = bookRepository;
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                serviceHandler.setService(bookLibraryService);
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
        serviceHandler.printContent("Please type the ID of the book you're returning: ");
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Back to Books screen\n");
        return optionsPrintFormat.toString();
    }

    public void returnBook(int id) {
        try {
            bookRepository.returnBook(id);
            serviceHandler.printUserActionRespone(SUCCESS);
        }
        catch (NonexistingLibraryItemError e) {
            serviceHandler.printUserActionRespone(NON_EXISTING_BOOK);
        }
        catch (AvailableLibraryItemError e) {
            serviceHandler.printUserActionRespone(ALREADY_AVAILABLE);
        }
    }
}
