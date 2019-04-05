package com.twu.biblioteca;

import com.twu.biblioteca.repositories.BookRepository;

public class ReturnService {

    private BookRepository bookRepository;
    public static final String SUCCESSFUL_MSG = "Thank you for returning the book.";
    public static final String UNSUCCESSFUL_MSG = "That is not a valid book to return.";

    public ReturnService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void returnBook(int id) {

    }


}
