package com.twu.biblioteca.repositories;

import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.errors.UnavailableBookError;
import com.twu.biblioteca.representations.Book;

import java.time.Year;
import java.util.*;

public class SampleBookRepository extends BookRepository {

    private Map<Integer, Book> books;

    public SampleBookRepository() {
        books = new HashMap<>();
        books.put(1, new Book(1, "A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        books.put(2, new Book(2, "The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        books.put(3, new Book(3, "Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
    }

    @Override
    public List<Book> viewAllBooks() {
        return (List<Book>) books.values();
    }

    @Override
    public void checkoutBook(int id) throws NonexistingBookError, UnavailableBookError {
        if (books.containsKey(id)) {
            books.get(id).checkout();
        }
        else {
            throw new NonexistingBookError();
        }
    }
}
