package com.twu.biblioteca;

import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;
import com.twu.biblioteca.representations.Option;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\n";
    public static final String NO_BOOKS_MESSAGE = "Sorry, we don't have any books at the moment.\n";
    private static final String NO_OPTION_ERROR = "Invalid option selected\n";
    private static final int HEADER_WIDTH = 20;
    private static final String BOOKS = "BOOKS";

    private BookRepository bookRepository;
    private List<Option> startScreenOptions;

    public static void main(String[] args) {
        BibliotecaApp app = new BibliotecaApp(null);
        app.run();
    }

    public BibliotecaApp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        setStartScreenOptions();
    }

    public void setStartScreenOptions() {
        startScreenOptions = new ArrayList<>();
        startScreenOptions.add(new Option("M", "Main Menu", "view_main_menu"));
        startScreenOptions.add(new Option("Q", "Quit Application", "quit"));
    }

    public void run() {
        printStartScreen();
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String optionSelected = scanner.next();
            selectOption(optionSelected);
        }
    }

    protected void printStartScreen() {
        System.out.print(WELCOME_MESSAGE);
        printStartScreenOptions();
    }

    private void printStartScreenOptions() {
        for (Option option : startScreenOptions) {
            System.out.println(option.getOptionPrintFormat());
        }
    }

    protected void selectOption(String optionString) {
        for (Option option : startScreenOptions) {
            if (optionString.toLowerCase().equals(option.getCode().toLowerCase())) {

                try {
                    Method method = getClass().getDeclaredMethod(option.getMethodName());
                    method.invoke(this, new Class[0]);
                } catch (Exception e) {
                    System.out.print(NO_OPTION_ERROR);
                }
                return;
            }
        }
        System.out.print(NO_OPTION_ERROR);
    }

    public void view_main_menu() {
        System.out.print("MAIN MENU");
    }

    public void quit() {

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
