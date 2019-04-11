package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Option;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReturnService extends ServiceManager {

    public static final String HEADER = "RETURN SERVICE";

    private BookRepository bookRepository;
    private BookScreenManager bookScreenManager;
    private List<Option> returnScreenOptions;


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
            uiHandler.printUserActionRespone("Thank you! Book #" + id + "is successfully returned!");
        }
        catch (NonexistingBookError e) {
            uiHandler.printUserActionRespone("Sorry, there is no Book#" + id);
        }
        catch (AvailableBookError e) {
            uiHandler.printUserActionRespone("Book#" + id + " is not checked out.");
        }
    }
}
