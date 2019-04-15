package com.twu.biblioteca;

import com.twu.biblioteca.exceptions.AvailableLibraryItemException;
import com.twu.biblioteca.exceptions.NonexistingLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class ReturnLibraryItemServiceTest {

    ServiceHandler serviceHandler;
    LibraryService bookLibraryServiceManager;
    LibraryRepository libraryRepository;
    ReturnLibraryItemService returnLibraryItemService;

    @Captor
    public ArgumentCaptor<String> uiHandlerPrintUserActionResponseCaptor = ArgumentCaptor.forClass(String.class);

    @Before
    public void setUpServiceHandlerLibraryServiceAndLibraryRepository() {
        serviceHandler = mock(ServiceHandler.class);
        bookLibraryServiceManager = mock(LibraryService.class);
        libraryRepository = mock(LibraryRepository.class);
        returnLibraryItemService = new ReturnLibraryItemService(serviceHandler, bookLibraryServiceManager, libraryRepository);

    }

    @Test
    public void shouldPrintSuccessResponseIfReturningACheckedOutBookUsingValidBookId() {
        returnLibraryItemService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnLibraryItemService.SUCCESS));
    }

    @Test
    public void shouldPrintNonexistingBookResponseIfReturningBookUsingInvalidBookId()
            throws NonexistingLibraryItemException, AvailableLibraryItemException {
        doThrow(new NonexistingLibraryItemException()).when(libraryRepository).returnLibraryItem(anyInt());
        returnLibraryItemService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnLibraryItemService.NON_EXISTING_LIBRARY_ITEM));
    }

    @Test
    public void shouldPrintAlreadyAvailableBookResponseIfReturningBookThatIsntCheckedOut()
            throws NonexistingLibraryItemException, AvailableLibraryItemException {
        doThrow(new AvailableLibraryItemException()).when(libraryRepository).returnLibraryItem(anyInt());
        returnLibraryItemService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnLibraryItemService.ALREADY_AVAILABLE_LIBRARY_ITEM));
    }


}


