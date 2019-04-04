package com.twu.biblioteca.repositories;

import com.twu.biblioteca.representations.Book;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class SampleBookRepository implements BookRepository {

    private List<Book> listOfBooks;

    public SampleBookRepository() {
        listOfBooks = new ArrayList<Book>();
        listOfBooks.add(new Book("A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        listOfBooks.add(new Book("The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        listOfBooks.add(new Book("Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
    }

    public List<Book> viewAllBooks() {
        return listOfBooks;
    }
}
