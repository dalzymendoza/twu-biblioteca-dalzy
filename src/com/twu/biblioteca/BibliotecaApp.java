package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.repositories.SampleBookRepository;
import com.twu.biblioteca.representations.Book;
import com.twu.biblioteca.representations.Option;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
    private static final int HEADER_WIDTH = 50;
    private static final String LIBRARY_NAME = "BIBLIOTECA";
    private static final String MAIN_MENU = "MAIN MENU";
    private static final String BOOKS = "BOOKS";

    private static final String QUIT_APP_CODE = "Q";
    private static final String BACK_CODE = "B";

    private BookRepository bookRepository;
    private CheckoutService checkoutService;
    private List<Option> startScreenOptions;
    private List<Option> mainMenuOptions;
    private List<Option> viewAllBooksScreenOptions;
    private List<Option> viewBookScreenOptions;
    private Scanner scanner;


    public static void main(String[] args) {
        BibliotecaApp app = new BibliotecaApp(new SampleBookRepository());
        app.run();
    }

    public BibliotecaApp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        assertNotNull(bookRepository);
        this.checkoutService = new CheckoutService(bookRepository);
        scanner = new Scanner(System.in);
        setStartScreenOptions();
        setMainMenuOptions();
        setViewAllBooksOptions();
        setViewBookOptions();
    }

    private void setStartScreenOptions() {
        startScreenOptions = new ArrayList<>();
        startScreenOptions.add(new Option("M", "Main Menu", "openMainMenuScreen", new Class[0]));
        startScreenOptions.add(new Option(QUIT_APP_CODE, "Quit Application", "quit", new Class[0]));
    }

    private void setMainMenuOptions() {
        mainMenuOptions = new ArrayList<>();
        mainMenuOptions.add(new Option("V", "View All Books", "openViewAllBooksScreen", new Class[0]));
        mainMenuOptions.add(new Option(BACK_CODE, "Go back to Start Screen", "run", new Class[0]));
    }

    private void setViewAllBooksOptions() {
        viewAllBooksScreenOptions = new ArrayList<>();
        viewAllBooksScreenOptions.add(new Option(BACK_CODE, "Go back to Main Menu", "openMainMenuScreen", new Class[0]));
    }

    private void setViewBookOptions() {
        viewBookScreenOptions = new ArrayList<>();
        viewBookScreenOptions.add(new Option("C", "Checkout this Book", "checkoutBook", new Class[]{Integer.class}));
        viewBookScreenOptions.add(new Option(BACK_CODE, "Go back to All Books", "openViewAllBooksScreen", new Class[0]));

    }

    public void run() {
        displayStartScreen();
        String optionSelected = scanner.next();
        selectOption(optionSelected, startScreenOptions, new Class[0]);
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
        selectOption(optionSelected, mainMenuOptions, new Class[0]);
    }

    public void quit() {
        scanner.close();
    }


    private void openViewAllBooksScreen() {
        displayAllBooks();
        System.out.println("openingviewallbooksscreen");
        printOptions(viewAllBooksScreenOptions);
        String optionSelected = scanner.next();
        if (isInteger(optionSelected)) {
            openBookScreen(Integer.parseInt(optionSelected));
        }
        else {
            selectOption(optionSelected, viewAllBooksScreenOptions, new Class[0]);
        }
    }

    protected void displayAllBooks() {
        printHeader(BOOKS);
        List<Book> books = bookRepository.viewAllBooks();
        if(books.size() == 0) {
            System.out.print(NO_BOOKS_MESSAGE);
            return;
        }
        for (Book book : books){
            if (book.getAvailability()) {
                System.out.println(book.getBookOptionPrintFormat());
            }
        }
    }

    private void openBookScreen(Integer id) {
        try {
            Book book = bookRepository.getBook(id);
            printHeader(book.getTitle());
        }
        catch(NonexistingBookError e) {
            System.out.print(NO_OPTION_ERROR);
        }
        printOptions(viewBookScreenOptions);
        String optionSelected = scanner.next();
        selectOption(optionSelected, viewBookScreenOptions, new Integer[]{id});
    }

    private void checkoutBook(Integer id) {
        checkoutService.checkoutBook(id);
        openViewAllBooksScreen();
    }

    private void printOptions(List<Option> options) {
        for (Option option : options) {
            System.out.println(option.getOptionPrintFormat());
        }
    }

    protected void selectOption(String optionString, List<Option> options, Object[] params) {
        for (Option option : options) {
            if (optionString.toLowerCase().equals(option.getCode().toLowerCase())) {

                try {
                    Method method = getClass().getDeclaredMethod(option.getMethodName(), option.getParameterTypes());
                    if (option.getParameterTypes().length > 0) {
                        method.invoke(this, params);
                    }
                    else {
                        method.invoke(this);
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    System.err.println(e.fillInStackTrace());
                    System.err.println(e.getStackTrace());
                    System.out.print(NO_OPTION_ERROR + ": " + optionString);
                }
                return;
            }
        }
        System.out.print(NO_OPTION_ERROR + ": " + optionString);
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

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


}


