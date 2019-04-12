package com.twu.biblioteca;

import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;

public class ViewBookService extends Service {

    private Book book;
    private BookLibraryService bookLibraryService;
    private CheckoutBookService checkoutBookService;
    private ServiceHandler serviceHandler;

    public ViewBookService(ServiceHandler serviceHandler, BookLibraryService bookLibraryService,
                           BookRepository bookRepository, Book book) {
        super(book.getTitle(), serviceHandler);
        this.serviceHandler = serviceHandler;
        this.book = book;
        this.bookLibraryService = bookLibraryService;
        this.checkoutBookService = new CheckoutBookService(serviceHandler, bookLibraryService,
                                        this, bookRepository);
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch(input) {
            case "B":
                serviceHandler.setService(bookLibraryService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "C":
                serviceHandler.setService(checkoutBookService);
                checkoutBookService.setBook(book);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            default:
                return ServiceHandler.InputProcessResponse.FAIL;
        }
    }

    @Override
    public void displayStartScreen() {
        serviceHandler.printHeader(book.getTitle());
        serviceHandler.printContent(book.getAuthorAndYearPrintFormat());
        serviceHandler.printContent(getOptonsPrintFormat());
    }

    private String getOptonsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[C] Checkout this book\n");
        optionsPrintFormat.append("[B] Back to Books screen\n");
        return optionsPrintFormat.toString();
    }

}
