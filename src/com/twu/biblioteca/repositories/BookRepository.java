package com.twu.biblioteca.repositories;

import com.twu.biblioteca.representations.Book;

import java.util.List;

public interface BookRepository {
    public List<Book> viewAllBooks();
}

