package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.repositories.SampleBookRepository;
import com.twu.biblioteca.representations.Book;

import java.util.List;

public class BookScreenManager extends ServiceManager {

    private static final String HEADER = "BOOKS";
    public static final String NO_BOOKS_MESSAGE = "Sorry, we don't have any books at the moment.\n";

    private BookRepository bookRepository;
    private HomeScreenManager homeScreenManager;
    private ReturnService returnService;

    public BookScreenManager(HomeScreenManager homeScreenManager, UIHandler uiHandler) {
        super(HEADER, uiHandler);
        this.homeScreenManager = homeScreenManager;
        this.bookRepository = new SampleBookRepository();
        this.returnService = new ReturnService(uiHandler, this, this.bookRepository);
    }

    @Override
    public UIHandler.InputProcessResponse processInput(String input) {
        switch (input) {
            case "B":
                uiHandler.setServiceManager(homeScreenManager);
                return UIHandler.InputProcessResponse.SUCCESS;
            case "R":
                System.out.println("Return Service");
                uiHandler.setServiceManager(returnService);
                return UIHandler.InputProcessResponse.SUCCESS;
            default:
                try {
                    int bookId = Integer.parseInt(input);
                    uiHandler.setServiceManager(new ViewService(uiHandler, this,
                                                                bookRepository, bookRepository.getBook(bookId)));
                    return UIHandler.InputProcessResponse.SUCCESS;
                }
                catch (NumberFormatException | NonexistingBookError e) {
                    return UIHandler.InputProcessResponse.FAIL;
                }
        }
    }

    @Override
    public void displayStartScreen() {
        if(uiHandler == null) {
            return;
        }
        uiHandler.printHeader(HEADER);
        uiHandler.printContent(getAllBooksPrintFormat());
        uiHandler.printContent(getOptionsPrintFormat());
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
                allBooksPrintFormat.append(book.getBookOptionPrintFormat() + "\n");
            }
        }
        return allBooksPrintFormat.toString();
    }
}
