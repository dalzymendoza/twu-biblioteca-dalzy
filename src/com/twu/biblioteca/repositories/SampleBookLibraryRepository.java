package com.twu.biblioteca.repositories;

import com.twu.biblioteca.representations.Book;
import com.twu.biblioteca.representations.LibraryItem;

import java.time.Year;
import java.util.*;

public class SampleBookLibraryRepository extends LibraryRepository {

    public SampleBookLibraryRepository() {
        super.setLibraryItemMap(generateSampleMapOf3Books());

    }

    private Map<Integer, LibraryItem> generateSampleMapOf3Books() {
        Map <Integer, LibraryItem> books = new HashMap<>();
        books.put(1, new Book(1, "A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        books.put(2, new Book(2, "The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        books.put(3, new Book(3, "Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
        return books;
    }

}
