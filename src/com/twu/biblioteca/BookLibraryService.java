package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingLibraryItemError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.repositories.SampleBookRepository;
import com.twu.biblioteca.representations.Book;

import java.util.List;

public class BookLibraryService extends Service {

    private static final String HEADER = "BOOKS";
    public static final String NO_BOOKS_MESSAGE = "Sorry, we don't have any books at the moment.\n";

    private BookRepository bookRepository;
    private HomeService homeService;
    private ReturnService returnService;

    public BookLibraryService(HomeService homeService, ServiceHandler serviceHandler) {
        super(HEADER, serviceHandler);
        this.homeService = homeService;
        this.bookRepository = new SampleBookRepository();
        this.returnService = new ReturnService(serviceHandler, this, this.bookRepository);
    }

    @Override
    public ServiceHandler.InputProcessResponse processInput(String input) {
        switch (input) {
            case "B":
                serviceHandler.setService(homeService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            case "R":
                System.out.println("Return Service");
                serviceHandler.setService(returnService);
                return ServiceHandler.InputProcessResponse.SUCCESS;
            default:
                try {
                    int bookId = Integer.parseInt(input);
                    serviceHandler.setService(new ViewBookService(serviceHandler, this,
                                                                bookRepository, bookRepository.getBook(bookId)));
                    return ServiceHandler.InputProcessResponse.SUCCESS;
                }
                catch (NumberFormatException | NonexistingLibraryItemError e) {
                    return ServiceHandler.InputProcessResponse.FAIL;
                }
        }
    }

    @Override
    public void displayStartScreen() {
        if(serviceHandler == null) {
            return;
        }
        serviceHandler.printHeader(HEADER);
        serviceHandler.printContent(getAllBooksPrintFormat());
        serviceHandler.printContent(getOptionsPrintFormat());
    }

    private String getOptionsPrintFormat() {
        StringBuilder optionsPrintFormat = new StringBuilder();
        optionsPrintFormat.append("[B] Back to Home screen\n");
        optionsPrintFormat.append("[R] Return a book\n");
        return optionsPrintFormat.toString();
    }

    private String getAllBooksPrintFormat() {
        List<Book> books = bookRepository.viewAllBooks();
        if(books.size() == 0) {
            return NO_BOOKS_MESSAGE;
        }
        StringBuilder allBooksPrintFormat = new StringBuilder();
        for (Book book : books){
            if (book.getAvailability()) {
                allBooksPrintFormat.append(book.getLibraryItemOptionPrintFormat() + "\n");
            }
        }
        return allBooksPrintFormat.toString();
    }
}
