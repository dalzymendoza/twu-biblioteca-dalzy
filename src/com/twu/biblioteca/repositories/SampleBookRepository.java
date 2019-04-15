package com.twu.biblioteca.repositories;

import com.twu.biblioteca.errors.AvailableLibraryItemError;
import com.twu.biblioteca.errors.NonexistingLibraryItemError;
import com.twu.biblioteca.errors.UnavailableLibraryItemError;
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
        return new ArrayList<>(books.values());
    }

    @Override
    public void checkoutBook(int id) throws NonexistingLibraryItemError, UnavailableLibraryItemError {
        if (books.containsKey(id)) {
            books.get(id).checkoutItem();
        }
        else {
            throw new NonexistingLibraryItemError();
        }
    }

    @Override
    public Book getBook(int id) throws NonexistingLibraryItemError {
        if (books.containsKey(id)) {
            return books.get(id);
        }
        throw new NonexistingLibraryItemError();
    }

    @Override
    public void returnBook(int id) throws NonexistingLibraryItemError, AvailableLibraryItemError {
        if (books.containsKey(id)) {
            Book book = books.get(id);
            if (book.getAvailability()) {
                throw new AvailableLibraryItemError();
            }
            else {
                book.returnItem();
            }

        }
        else {
            throw new NonexistingLibraryItemError();
        }
    }
}
