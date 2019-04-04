package com.twu.biblioteca;


import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BibliotecaAppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void correct_welcome_message() {
        BibliotecaApp.main(new String[0]);
        assertThat(outContent.toString(),
                containsString(BibliotecaApp.WELCOME_MESSAGE));
    }

    @Test
    public void view_empty_list_of_books() {
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.viewAllBooks()).thenReturn(new ArrayList<Book>());
        BibliotecaApp app = new BibliotecaApp(bookRepository);

        app.viewAllBooks();
        assertThat(outContent.toString(),
                containsString(BibliotecaApp.NO_BOOKS_MESSAGE));
    }

    @Test
    public void view_non_empty_list_of_books() {
        BookRepository bookRepository = mock(BookRepository.class);
        List<Book> testListOf3Books = generateTestListOf3Books();
        when(bookRepository.viewAllBooks()).thenReturn(testListOf3Books);
        BibliotecaApp app = new BibliotecaApp(bookRepository);

        app.viewAllBooks();
        String actualOutput = outContent.toString();
        for (Book book : testListOf3Books) {
            assertThat(actualOutput, containsString(book.getTitle()));
        }

    }

    private List<Book> generateTestListOf3Books() {
        List<Book> listOfBooks = new ArrayList<Book>();
        listOfBooks.add(new Book("A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        listOfBooks.add(new Book("The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        listOfBooks.add(new Book("Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
        return listOfBooks;
    }


}
