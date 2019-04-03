package com.twu.biblioteca;

import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;

import java.util.List;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\n";
    public static final String NO_BOOKS_MESSAGE = "Sorry, we don't have any books at the moment.\n";
    private static final int HEADER_WIDTH = 20;
    private static final String BOOKS = "BOOKS";

    private BookRepository bookRepository;

    public static void main(String[] args) {
        System.out.print(WELCOME_MESSAGE);
    }

    public BibliotecaApp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void viewAllBooks() {
        printHeader(BOOKS);
        List<Book> books = bookRepository.viewAllBooks();
        if(books.size() == 0) {
            System.out.print(NO_BOOKS_MESSAGE);
            return;
        }
        for (Book book : books){
            System.out.println(book.getTitle());
        }

    }

    public void printHeader(String headerTitle){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < HEADER_WIDTH; i++) {
            builder.append("=");
        }
        builder.append("\n" + headerTitle);
        for (int i = 0; i < HEADER_WIDTH; i++) {
            builder.append("=");
        }
        builder.append("\n");
        System.out.print(builder);
    }
}
