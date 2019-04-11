package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.errors.UnavailableBookError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;


public class CheckoutService extends ServiceManager{

    public static final String NOT_AVAILABLE_BOOK = "Sorry, that book is not available.";
    public static final String NONEXISTING_BOOK = "Sorry, this book does not exist.";
    public static final String SUCCESS = "Thank you! Enjoy the book.";

    public static final String HEADER = "CHECKOUT SERVICE";

    private BookRepository bookRepository;
    private ViewService viewService;
    private BookScreenManager bookScreenManager;
    private UIHandler uiHandler;
    private Book book;

    public CheckoutService(UIHandler uiHandler, BookScreenManager bookScreenManager,
                           ViewService viewService, BookRepository bookRepository) {
        super(HEADER, uiHandler);
        this.uiHandler = uiHandler;
        this.viewService = viewService;
        this.bookRepository = bookRepository;
        this.bookScreenManager = bookScreenManager;
    }

    @Override
    public UIHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "Y":
                checkoutBook(book.getId());
                uiHandler.setServiceManager(bookScreenManager);
                return UIHandler.InputProcessResponse.SUCCESS;
            case "N":
                uiHandler.setServiceManager(viewService);
                return UIHandler.InputProcessResponse.SUCCESS;
            default:
                return UIHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        if (book == null) {
            uiHandler.printUserActionRespone("No book selected for checkout");
            uiHandler.setServiceManager(viewService);
        }
        else {
            uiHandler.printHeader("Checking out " + book.getTitle());
            uiHandler.printContent(getOptionsPrintFormat());
        }

    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[Y] Yes, check out.\n");
        optionsPrintFormat.append("[N] No. \n");
        return optionsPrintFormat.toString();
    }

    public void checkoutBook(int id) {
        try {
            bookRepository.checkoutBook(id);
            uiHandler.printUserActionRespone(SUCCESS);
        }
        catch (UnavailableBookError e) {
            uiHandler.printUserActionRespone(NOT_AVAILABLE_BOOK);
        }
        catch (NonexistingBookError e) {
            uiHandler.printUserActionRespone(NONEXISTING_BOOK);
        }
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
