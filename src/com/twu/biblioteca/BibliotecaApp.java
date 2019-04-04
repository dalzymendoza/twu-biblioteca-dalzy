package com.twu.biblioteca;

import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.repositories.SampleBookRepository;
import com.twu.biblioteca.representations.Book;
import com.twu.biblioteca.representations.Option;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\n";
    public static final String NO_BOOKS_MESSAGE = "Sorry, we don't have any books at the moment.\n";
    private static final String NO_OPTION_ERROR = "Invalid option selected\n";
    private static final int HEADER_WIDTH = 40;
    private static final String LIBRARY_NAME = "BIBLIOTECA";
    private static final String MAIN_MENU = "MAIN MENU";
    private static final String BOOKS = "BOOKS";

    private static final String QUIT_APP_CODE = "Q";
    private static final String BACK_CODE = "B";

    private BookRepository bookRepository;
    private List<Option> startScreenOptions;
    private List<Option> mainMenuOptions;
    private List<Option> viewBooksOptions;
    private Scanner scanner;


    public static void main(String[] args) {
        BibliotecaApp app = new BibliotecaApp(new SampleBookRepository());
        app.run();
    }

    public BibliotecaApp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        assertNotNull(bookRepository);
        scanner = new Scanner(System.in);
        setStartScreenOptions();
        setMainMenuOptions();
        setViewBooksOptions();
    }

    private void setStartScreenOptions() {
        startScreenOptions = new ArrayList<>();
        startScreenOptions.add(new Option("M", "Main Menu", "openMainMenuScreen"));
        startScreenOptions.add(new Option(QUIT_APP_CODE, "Quit Application", "quit"));
    }

    private void setMainMenuOptions() {
        mainMenuOptions = new ArrayList<>();
        mainMenuOptions.add(new Option("V", "View All Books", "openViewAllBooksScreen"));
        mainMenuOptions.add(new Option("C", "Checkout a Book", "checkOutBook"));
        mainMenuOptions.add(new Option("R", "Return a Book", "returnBook"));
        mainMenuOptions.add(new Option(BACK_CODE, "Go back to Start Screen", "displayStartScreen"));
    }

    private void setViewBooksOptions() {
        viewBooksOptions = new ArrayList<>();
        viewBooksOptions.add(new Option(BACK_CODE, "Go back to Main Menu", "openMainMenuScreen"));
    }

    public void run() {
        displayStartScreen();
        String optionSelected = scanner.next();
        selectOption(optionSelected, startScreenOptions);
    }

    protected void displayStartScreen() {
        printHeader(LIBRARY_NAME);
        System.out.print(WELCOME_MESSAGE);
        printOptions(startScreenOptions);
    }

    protected void openMainMenuScreen() {
        printHeader(MAIN_MENU);
        printOptions(mainMenuOptions);
        String optionSelected = scanner.next();
        selectOption(optionSelected, mainMenuOptions);
    }

    public void quit() {
        scanner.close();
    }

    private void openViewAllBooksScreen() {
        displayAllBooks();
        printOptions(viewBooksOptions);
        String optionSelected = scanner.next();
        selectOption(optionSelected, viewBooksOptions);
    }

    protected void displayAllBooks() {
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

    private void printOptions(List<Option> options) {
        for (Option option : options) {
            System.out.println(option.getOptionPrintFormat());
        }
    }

    protected void selectOption(String optionString, List<Option> options) {
        for (Option option : options) {
            if (optionString.toLowerCase().equals(option.getCode().toLowerCase())) {

                try {
                    Method method = getClass().getDeclaredMethod(option.getMethodName());
                    method.invoke(this, new Class[0]);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    System.out.print(NO_OPTION_ERROR);
                }
                return;
            }
        }
        System.out.print(NO_OPTION_ERROR);
    }

    public void printHeader(String headerTitle){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < HEADER_WIDTH; i++) {
            builder.append("=");
        }
        builder.append("\n" + headerTitle + "\n");
        for (int i = 0; i < HEADER_WIDTH; i++) {
            builder.append("=");
        }
        builder.append("\n");
        System.out.print(builder);
    }

}


