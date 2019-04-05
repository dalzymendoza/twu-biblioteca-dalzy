package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.errors.UnavailableBookError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReturnServiceTest {

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
    public void returnCheckedOutBookUsingCorrectId() {
        BookRepository bookRepository = mock(BookRepository.class);
        ReturnService service = new ReturnService(bookRepository);
        service.returnBook(1);
        assertThat(outContent.toString(), containsString(ReturnService.SUCCESSFUL_MSG));
    }

    @Test
    public void returnCheckedOutBookUsingIncorrectId() throws NonexistingBookError, AvailableBookError {
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new NonexistingBookError()).when(bookRepository).returnBook(anyInt());
        ReturnService service = new ReturnService(bookRepository);
        service.returnBook(1);
        assertThat(outContent.toString(), containsString(ReturnService.UNSUCCESSFUL_MSG));
    }

    @Test
    public void returnBookNotCheckedOut() throws NonexistingBookError, AvailableBookError {
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new AvailableBookError()).when(bookRepository).returnBook(anyInt());
        ReturnService service = new ReturnService(bookRepository);
        service.returnBook(1);
        assertThat(outContent.toString(), containsString(ReturnService.UNSUCCESSFUL_MSG));
    }
}


