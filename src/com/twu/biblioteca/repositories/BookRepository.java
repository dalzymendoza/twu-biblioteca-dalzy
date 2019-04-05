package com.twu.biblioteca.repositories;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.errors.UnavailableBookError;
import com.twu.biblioteca.representations.Book;
import org.junit.Test;

import java.util.List;

public abstract class BookRepository {

    public abstract List<Book> viewAllBooks();
    public abstract void checkoutBook(int id)  throws NonexistingBookError, UnavailableBookError;
    public abstract Book getBook(int id) throws NonexistingBookError;
    public abstract void returnBook(int id) throws NonexistingBookError, AvailableBookError;
}

