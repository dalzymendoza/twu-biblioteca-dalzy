package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.repositories.BookRepository;

public class ReturnService extends ServiceManager {

    public static final String HEADER = "RETURN SERVICE";

    public static final String SUCCESS = "Thank you! Book has been successfully returned!";
    public static final String NON_EXISTING_BOOK = "Sorry, there is no such book in our system.";
    public static final String ALREADY_AVAILABLE = "That book is not checked out.";

    private BookRepository bookRepository;
    private BookScreenManager bookScreenManager;


    public ReturnService(UIHandler uiHandler, BookScreenManager bookScreenManager, BookRepository bookRepository) {
        super(HEADER, uiHandler);
        this.bookScreenManager = bookScreenManager;
        this.bookRepository = bookRepository;
    }

    @Override
    public UIHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                uiHandler.setServiceManager(bookScreenManager);
                return UIHandler.InputProcessResponse.SUCCESS;
            default:
                try {
                    int bookId = Integer.parseInt(input);
                    returnBook(bookId);
                    return UIHandler.InputProcessResponse.SUCCESS;
                }
                catch (NumberFormatException e) {
                    return UIHandler.InputProcessResponse.FAIL;
                }
        }
    }

    @Override
    public void displayStartScreen() {
        uiHandler.printHeader(HEADER);
        uiHandler.printContent(getOptionsPrintFormat());
        uiHandler.printContent("Please type the ID of the book you're returning: ");
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Back to Books screen\n");
        return optionsPrintFormat.toString();
    }

    public void returnBook(int id) {
        try {
            bookRepository.returnBook(id);
            uiHandler.printUserActionRespone(SUCCESS);
        }
        catch (NonexistingBookError e) {
            uiHandler.printUserActionRespone(NON_EXISTING_BOOK);
        }
        catch (AvailableBookError e) {
            uiHandler.printUserActionRespone(ALREADY_AVAILABLE);
        }
    }
}
