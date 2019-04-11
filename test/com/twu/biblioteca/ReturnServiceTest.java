package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.repositories.BookRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

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
    public void shouldGetSuccessResponseIfReturningACheckedOutBookUsingValidBookId() {
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        BookRepository bookRepository = mock(BookRepository.class);

        ReturnService returnService = new ReturnService(uiHandler, bookScreenManager, bookRepository);
        assertThat(returnService.returnBook(1), is(ReturnService.ReturnResponse.SUCCESS));
    }

    @Test
    public void shouldGetNonexistingBookResponseIfReturningBookUsingInvalidBookId()
            throws NonexistingBookError, AvailableBookError{
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new NonexistingBookError()).when(bookRepository).returnBook(anyInt());

        ReturnService returnService = new ReturnService(uiHandler, bookScreenManager, bookRepository);
        assertThat(returnService.returnBook(1), is(ReturnService.ReturnResponse.NONEXISTING_BOOK));
    }

    @Test
    public void shouldGetAlreadyAvailableBookResponseIfReturningBookThatIsntCheckedOut() throws NonexistingBookError, AvailableBookError {
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new AvailableBookError()).when(bookRepository).returnBook(anyInt());

        ReturnService returnService = new ReturnService(uiHandler, bookScreenManager, bookRepository);
        assertThat(returnService.returnBook(1), is(ReturnService.ReturnResponse.ALREADY_AVAILABLE));
    }
}


