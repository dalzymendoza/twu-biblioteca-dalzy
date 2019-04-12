package com.twu.biblioteca;

import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;

public class ViewBookService extends Service {

    private Book book;
    private BookLibraryService bookLibraryService;
    private CheckoutBookService checkoutBookService;
    private UIHandler uiHandler;

    public ViewBookService(UIHandler uiHandler, BookLibraryService bookLibraryService,
                           BookRepository bookRepository, Book book) {
        super(book.getTitle(), uiHandler);
        this.uiHandler = uiHandler;
        this.book = book;
        this.bookLibraryService = bookLibraryService;
        this.checkoutBookService = new CheckoutBookService(uiHandler, bookLibraryService,
                                        this, bookRepository);
    }

    @Override
    public UIHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                uiHandler.setService(bookLibraryService);
                return UIHandler.InputProcessResponse.SUCCESS;
            case "C":
                uiHandler.setService(checkoutBookService);
                checkoutBookService.setBook(book);
                return UIHandler.InputProcessResponse.SUCCESS;
            default:
                return UIHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        uiHandler.printHeader(book.getTitle());
        uiHandler.printContent(book.getAuthorAndYearPrintFormat());
        uiHandler.printContent(getOptonsPrintFormat());
    }

    private String getOptonsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[C] Checkout this book\n");
        optionsPrintFormat.append("[B] Back to Books screen\n");
        return optionsPrintFormat.toString();
    }

}
