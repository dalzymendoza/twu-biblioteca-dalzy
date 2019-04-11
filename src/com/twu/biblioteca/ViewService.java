package com.twu.biblioteca;

import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;

public class ViewService extends ServiceManager {

    private Book book;
    private BookScreenManager bookScreenManager;
    private CheckoutService checkoutService;
    private UIHandler uiHandler;

    public ViewService(UIHandler uiHandler, BookScreenManager bookScreenManager,
                       BookRepository bookRepository, Book book) {
        super(book.getTitle(), uiHandler);
        this.uiHandler = uiHandler;
        this.book = book;
        this.bookScreenManager = bookScreenManager;
        this.checkoutService = new CheckoutService(uiHandler, bookScreenManager,
                                        this, bookRepository);
    }

    @Override
    public UIHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                uiHandler.setServiceManager(bookScreenManager);
                return UIHandler.InputProcessResponse.SUCCESS;
            case "C":
                uiHandler.setServiceManager(checkoutService);
                checkoutService.setBook(book);
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
