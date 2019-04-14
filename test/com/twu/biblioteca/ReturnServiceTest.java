package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableLibraryItemError;
import com.twu.biblioteca.errors.NonexistingLibraryItemError;
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
            throws NonexistingLibraryItemError, AvailableLibraryItemError {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        BookLibraryService bookLibraryServiceManager = mock(BookLibraryService.class);
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new NonexistingLibraryItemError()).when(bookRepository).returnBook(anyInt());

        ReturnService returnService = new ReturnService(serviceHandler, bookLibraryServiceManager, bookRepository);
        returnService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnService.NON_EXISTING_BOOK));
    }

    @Test
    public void shouldPrintAlreadyAvailableBookResponseIfReturningBookThatIsntCheckedOut()
            throws NonexistingLibraryItemError, AvailableLibraryItemError {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        BookLibraryService bookLibraryServiceManager = mock(BookLibraryService.class);
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new AvailableLibraryItemError()).when(bookRepository).returnBook(anyInt());

        ReturnService returnService = new ReturnService(serviceHandler, bookLibraryServiceManager, bookRepository);
        returnService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnService.ALREADY_AVAILABLE));
    }
}


