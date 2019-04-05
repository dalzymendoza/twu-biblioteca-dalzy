package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;
import com.twu.biblioteca.representations.Option;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReturnService {

    public static final String SUCCESSFUL_MSG = "Thank you for returning the book.";
    public static final String UNSUCCESSFUL_MSG = "That is not a valid book to return.";
    public static final String SCREEN_HEADER = "RETURN SERVICE";

    private BookRepository bookRepository;
    private BibliotecaApp app;
    private List<Option> returnScreenOptions;
    private Scanner scanner;


    public ReturnService(BibliotecaApp app, BookRepository bookRepository, Scanner scanner) {
        this.app = app;
        this.bookRepository = bookRepository;
        this.scanner = scanner;
        setreturnScreenOptions();
    }

    private void setreturnScreenOptions() {
        returnScreenOptions = new ArrayList<>();
        returnScreenOptions.add(new Option(BibliotecaApp.BACK_CODE, "Back to Main Menu", "closeReturnScreen", new Class[0]));
    }

    public void closeReturnScreen() {
        app.openMainMenuScreen();
    }

    public void openReturnScreen() {
        app.printHeader(SCREEN_HEADER);
        app.printOptions(returnScreenOptions);
        System.out.print("Please type the ID of the book you're returning: ");

        boolean optionSelectedIsSuccess = false;
        while (!optionSelectedIsSuccess) {
            String optionSelected = scanner.next();
            if (BibliotecaApp.isInteger(optionSelected)) {
                returnBook(Integer.parseInt(optionSelected));
            }
            else {
                try {
                    selectOption(optionSelected, returnScreenOptions, new Object[0]);
                    optionSelectedIsSuccess = true;
                }
                catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    System.err.println(e.fillInStackTrace());
                    System.err.println(e.getStackTrace());
                    System.out.println(BibliotecaApp.NO_OPTION_ERROR + ": " + optionSelected);
                }
            }
        }
    }

    public void returnBook(int id) {
        try {
            bookRepository.returnBook(id);
            System.out.println(BibliotecaApp.RESPONSE_MARKER + " " + SUCCESSFUL_MSG);
        }
        catch (AvailableBookError | NonexistingBookError e) {
            System.out.println(BibliotecaApp.RESPONSE_MARKER + " " + UNSUCCESSFUL_MSG);
        }
    }

    private void selectOption(String optionString, List<Option> options, Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (Option option : options) {
            if (optionString.toLowerCase().equals(option.getCode().toLowerCase())) {

                Method method = getClass().getDeclaredMethod(option.getMethodName(), option.getParameterTypes());
                if (option.getParameterTypes().length > 0) {
                    method.invoke(this, params);
                    return;
                }
                else {
                    method.invoke(this);
                    return;
                }

            }
        }
        throw new NoSuchMethodException();
    }


}
