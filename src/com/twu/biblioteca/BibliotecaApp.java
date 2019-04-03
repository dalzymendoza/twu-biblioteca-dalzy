package com.twu.biblioteca;

import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;

public class BibliotecaApp {

    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!";

    private BookRepository bookRepository;

    public static void main(String[] args) {
        System.out.print(WELCOME_MESSAGE);
    }

    public BibliotecaApp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void viewAllBooks() {

    }
}
