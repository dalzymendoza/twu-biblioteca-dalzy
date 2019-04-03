package com.twu.biblioteca;


import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
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
                is("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!"));
    }

    @Test
    public void view_empty_list_of_books() {
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.viewAllBooks()).thenReturn(new ArrayList<Book>());
        BibliotecaApp app = new BibliotecaApp(bookRepository);

        app.viewAllBooks();
        assertThat(outContent.toString(),
                is("Sorry, we don't have any books at the moment."));
    }
}
