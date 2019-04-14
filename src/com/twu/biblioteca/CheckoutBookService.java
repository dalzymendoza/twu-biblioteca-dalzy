package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingLibraryItemError;
import com.twu.biblioteca.errors.UnavailableLibraryItemError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;


public class CheckoutBookService extends Service {

    public static final String NOT_AVAILABLE_BOOK = "Sorry, that book is not available.";
    public static final String NONEXISTING_BOOK = "Sorry, this book does not exist.";
    public static final String SUCCESS = "Thank you! Enjoy the book.";

    public static final String HEADER = "CHECKOUT SERVICE";

    private BookRepository bookRepository;
    private ViewBookService viewBookService;
    private BookLibraryService bookLibraryService;
    private ServiceHandler serviceHandler;
    private Book book;

    public CheckoutBookService(ServiceHandler serviceHandler, BookLibraryService bookLibraryService,
                               ViewBookService viewBookService, BookRepository bookRepository) {
        super(HEADER, serviceHandler);
        this.serviceHandler = serviceHandler;
        this.viewBookService = viewBookService;
        this.bookRepository = bookRepository;
        this.bookLibraryService = bookLibraryService;
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "Y":
                checkoutBook(book.getId());
                serviceHandler.setService(bookLibraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "N":
                serviceHandler.setService(viewBookService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            default:
                return ServiceHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        if (book == null) {
            serviceHandler.printUserActionRespone("No book selected for checkout");
            serviceHandler.setService(viewBookService);
        }
        else {
            serviceHandler.printHeader("Checking out " + book.getTitle());
            serviceHandler.printContent(getOptionsPrintFormat());
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
            serviceHandler.printUserActionRespone(SUCCESS);
        }
        catch (UnavailableLibraryItemError e) {
            serviceHandler.printUserActionRespone(NOT_AVAILABLE_BOOK);
        }
        catch (NonexistingLibraryItemError e) {
            serviceHandler.printUserActionRespone(NONEXISTING_BOOK);
        }
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
