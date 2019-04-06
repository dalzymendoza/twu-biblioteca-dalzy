package com.twu.biblioteca;


import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class BibliotecaAppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Before
    public void setOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    public void correctWelcomeMessage() {
        BookRepository bookRepository = mock(BookRepository.class);
        BibliotecaApp app = new BibliotecaApp(bookRepository);
        app.displayStartScreen();
        assertThat(outContent.toString(),
                containsString(BibliotecaApp.WELCOME_MESSAGE));
    }

    @Test
    public void viewEmptyListOfBooks() {
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.viewAllBooks()).thenReturn(new ArrayList<Book>());
        BibliotecaApp app = new BibliotecaApp(bookRepository);

        app.displayAllBooks();
        assertThat(outContent.toString(),
                containsString(BibliotecaApp.NO_BOOKS_MESSAGE));
    }

    @Test
    public void viewNonEmptyListOfBooks() {
        BookRepository bookRepository = mock(BookRepository.class);
        List<Book> testListOf3Books = generateTestListOf3Books();
        when(bookRepository.viewAllBooks()).thenReturn(testListOf3Books);
        BibliotecaApp app = new BibliotecaApp(bookRepository);

        app.displayAllBooks();
        String actualOutput = outContent.toString();
        for (Book book : testListOf3Books) {
            assertThat(actualOutput, containsString(book.getTitle()));
        }

    }

    private List<Book> generateTestListOf3Books() {
        List<Book> listOfBooks = new ArrayList<Book>();
        listOfBooks.add(new Book(1, "A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        listOfBooks.add(new Book(2, "The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        listOfBooks.add(new Book(3, "Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
        return listOfBooks;
    }


}
