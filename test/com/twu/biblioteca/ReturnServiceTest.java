package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableLibraryItemError;
import com.twu.biblioteca.errors.NonexistingLibraryItemError;
import com.twu.biblioteca.repositories.LibraryRepository;
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
        LibraryService bookLibraryServiceManager = mock(LibraryService.class);
        LibraryRepository libraryRepository = mock(LibraryRepository.class);

        ReturnService returnService = new ReturnService(serviceHandler, bookLibraryServiceManager, libraryRepository);
        returnService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnService.SUCCESS));
    }

    @Test
    public void shouldPrintNonexistingBookResponseIfReturningBookUsingInvalidBookId()
            throws NonexistingLibraryItemError, AvailableLibraryItemError {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        LibraryService bookLibraryServiceManager = mock(LibraryService.class);
        LibraryRepository libraryRepository = mock(LibraryRepository.class);
        doThrow(new NonexistingLibraryItemError()).when(libraryRepository).returnLibraryItem(anyInt());

        ReturnService returnService = new ReturnService(serviceHandler, bookLibraryServiceManager, libraryRepository);
        returnService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnService.NON_EXISTING_LIBRARY_ITEM));
    }

    @Test
    public void shouldPrintAlreadyAvailableBookResponseIfReturningBookThatIsntCheckedOut()
            throws NonexistingLibraryItemError, AvailableLibraryItemError {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        LibraryService bookLibraryServiceManager = mock(LibraryService.class);
        LibraryRepository libraryRepository = mock(LibraryRepository.class);
        doThrow(new AvailableLibraryItemError()).when(libraryRepository).returnLibraryItem(anyInt());

        ReturnService returnService = new ReturnService(serviceHandler, bookLibraryServiceManager, libraryRepository);
        returnService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnService.ALREADY_AVAILABLE_LIBRARY_ITEM));
    }
}


