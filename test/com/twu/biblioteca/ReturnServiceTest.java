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
    public void shouldPrintSuccessResponseIfReturningACheckedOutBookUsingValidBookId() {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        BookLibraryService bookLibraryServiceManager = mock(BookLibraryService.class);
        BookRepository bookRepository = mock(BookRepository.class);

        ReturnService returnService = new ReturnService(serviceHandler, bookLibraryServiceManager, bookRepository);
        returnService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnService.SUCCESS));
    }

    @Test
    public void shouldPrintNonexistingBookResponseIfReturningBookUsingInvalidBookId()
            throws NonexistingBookError, AvailableBookError{
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        BookLibraryService bookLibraryServiceManager = mock(BookLibraryService.class);
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new NonexistingBookError()).when(bookRepository).returnBook(anyInt());

        ReturnService returnService = new ReturnService(serviceHandler, bookLibraryServiceManager, bookRepository);
        returnService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnService.NON_EXISTING_BOOK));
    }

    @Test
    public void shouldPrintAlreadyAvailableBookResponseIfReturningBookThatIsntCheckedOut()
            throws NonexistingBookError, AvailableBookError {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        BookLibraryService bookLibraryServiceManager = mock(BookLibraryService.class);
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new AvailableBookError()).when(bookRepository).returnBook(anyInt());

        ReturnService returnService = new ReturnService(serviceHandler, bookLibraryServiceManager, bookRepository);
        returnService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnService.ALREADY_AVAILABLE));
    }
}


