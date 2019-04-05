package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;

import java.util.Scanner;

public class ReturnService {

    private BookRepository bookRepository;
    private BibliotecaApp app;
    public static final String SUCCESSFUL_MSG = "Thank you for returning the book.";
    public static final String UNSUCCESSFUL_MSG = "That is not a valid book to return.";
    public static final String SCREEN_HEADER = "RETURN SERVICE";

    public ReturnService(BibliotecaApp app, BookRepository bookRepository) {
        this.app = app;
        this.bookRepository = bookRepository;
    }

    public void openReturnScreen() {
        app.printHeader(SCREEN_HEADER);
        System.out.print("Please type the ID of the book you're returning: ");

    }

    private int askForIdInput() {
        Scanner scanner = new Scanner(System.in); // TODO: check that opening another scanner for system.in is okay
        String returnId = scanner.next();
        return 0;
    }

    public void returnBook(int id) {
        try {
            bookRepository.returnBook(id);
            System.out.println(BibliotecaApp.RESPONSE_MARKER + " " + SUCCESSFUL_MSG);
        }
        catch (AvailableBookError | NonexistingBookError e) {
            System.out.println(BibliotecaApp.RESPONSE_MARKER + " " + UNSUCCESSFUL_MSG);
        }
    }


}
