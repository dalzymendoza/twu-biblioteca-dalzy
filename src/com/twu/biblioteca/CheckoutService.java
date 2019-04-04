package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.errors.UnavailableBookError;
import com.twu.biblioteca.repositories.BookRepository;

public class CheckoutService {

    public static final String UNAVAILABLE_MSG = "Sorry, this book is unavailable for checkout.";
    public static final String NONEXISTING_MSG = "Sorry, this book does not exist.";

    private BookRepository bookRepository;

    public CheckoutService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void checkoutBook(int id) {
        try {
            bookRepository.checkoutBook(id);
        }
        catch (UnavailableBookError e) {
            System.out.println(UNAVAILABLE_MSG);
        }
        catch (NonexistingBookError e) {
            System.out.println(NONEXISTING_MSG);
        }
    }
}
