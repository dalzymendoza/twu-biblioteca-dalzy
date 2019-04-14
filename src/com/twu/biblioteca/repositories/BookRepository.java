package com.twu.biblioteca.repositories;

import com.twu.biblioteca.errors.AvailableLibraryItemError;
import com.twu.biblioteca.errors.NonexistingLibraryItemError;
import com.twu.biblioteca.errors.UnavailableLibraryItemError;
import com.twu.biblioteca.representations.Book;

import java.util.List;

public abstract class BookRepository {

    public abstract List<Book> viewAllBooks();
    public abstract void checkoutBook(int id)  throws NonexistingLibraryItemError, UnavailableLibraryItemError;
    public abstract Book getBook(int id) throws NonexistingLibraryItemError;
    public abstract void returnBook(int id) throws NonexistingLibraryItemError, AvailableLibraryItemError;
}

