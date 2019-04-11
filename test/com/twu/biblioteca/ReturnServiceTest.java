package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.repositories.BookRepository;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class ReturnServiceTest {


    @Captor
    public ArgumentCaptor<String> uiHandlerPrintUserActionResponseCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void shouldGetSuccessResponseIfReturningACheckedOutBookUsingValidBookId() {
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        BookRepository bookRepository = mock(BookRepository.class);
        ArgumentCaptor<String> printContentArgument = ArgumentCaptor.forClass(String.class);

        ReturnService returnService = new ReturnService(uiHandler, bookScreenManager, bookRepository);
        returnService.returnBook(1);
        verify(uiHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue().toString(), containsString(ReturnService.SUCCESS));
    }

    @Test
    public void shouldGetNonexistingBookResponseIfReturningBookUsingInvalidBookId()
            throws NonexistingBookError, AvailableBookError{
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new NonexistingBookError()).when(bookRepository).returnBook(anyInt());

        ReturnService returnService = new ReturnService(uiHandler, bookScreenManager, bookRepository);
        returnService.returnBook(1);
        verify(uiHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue().toString(), containsString(ReturnService.NON_EXISTING_BOOK));
    }

    @Test
    public void shouldGetAlreadyAvailableBookResponseIfReturningBookThatIsntCheckedOut()
            throws NonexistingBookError, AvailableBookError {
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new AvailableBookError()).when(bookRepository).returnBook(anyInt());

        ReturnService returnService = new ReturnService(uiHandler, bookScreenManager, bookRepository);
        returnService.returnBook(1);
        verify(uiHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue().toString(), containsString(ReturnService.ALREADY_AVAILABLE));
    }
}


