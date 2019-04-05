package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.errors.UnavailableBookError;
import com.twu.biblioteca.repositories.BookRepository;

import javax.swing.plaf.synth.SynthColorChooserUI;

public class CheckoutService {

    public static final String UNAVAILABLE_MSG = "Sorry, that book is not available.";
    public static final String NONEXISTING_MSG = "Sorry, this book does not exist.";
    public static final String SUCCESSFUL = "Thank you! Enjoy the book.";


    private BookRepository bookRepository;

    public CheckoutService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void checkoutBook(int id) {
        try {
            bookRepository.checkoutBook(id);
            System.out.println(BibliotecaApp.RESPONSE_MARKER + " " + SUCCESSFUL);
        }
        catch (UnavailableBookError e) {
            System.out.println(BibliotecaApp.RESPONSE_MARKER + " " + UNAVAILABLE_MSG);
        }
        catch (NonexistingBookError e) {
            System.out.println(BibliotecaApp.RESPONSE_MARKER + " " + NONEXISTING_MSG);
        }
    }
}
