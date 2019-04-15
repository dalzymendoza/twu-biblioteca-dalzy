package com.twu.biblioteca;

import com.twu.biblioteca.errors.AvailableLibraryItemException;
import com.twu.biblioteca.errors.NonexistingLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class ReturnLibraryItemServiceTest {

    @Captor
    public ArgumentCaptor<String> uiHandlerPrintUserActionResponseCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void shouldPrintSuccessResponseIfReturningACheckedOutBookUsingValidBookId() {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        LibraryService bookLibraryServiceManager = mock(LibraryService.class);
        LibraryRepository libraryRepository = mock(LibraryRepository.class);

        ReturnLibraryItemService returnLibraryItemService = new ReturnLibraryItemService(serviceHandler, bookLibraryServiceManager, libraryRepository);
        returnLibraryItemService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnLibraryItemService.SUCCESS));
    }

    @Test
    public void shouldPrintNonexistingBookResponseIfReturningBookUsingInvalidBookId()
            throws NonexistingLibraryItemException, AvailableLibraryItemException {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        LibraryService bookLibraryServiceManager = mock(LibraryService.class);
        LibraryRepository libraryRepository = mock(LibraryRepository.class);
        doThrow(new NonexistingLibraryItemException()).when(libraryRepository).returnLibraryItem(anyInt());

        ReturnLibraryItemService returnLibraryItemService = new ReturnLibraryItemService(serviceHandler, bookLibraryServiceManager, libraryRepository);
        returnLibraryItemService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnLibraryItemService.NON_EXISTING_LIBRARY_ITEM));
    }

    @Test
    public void shouldPrintAlreadyAvailableBookResponseIfReturningBookThatIsntCheckedOut()
            throws NonexistingLibraryItemException, AvailableLibraryItemException {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        LibraryService bookLibraryServiceManager = mock(LibraryService.class);
        LibraryRepository libraryRepository = mock(LibraryRepository.class);
        doThrow(new AvailableLibraryItemException()).when(libraryRepository).returnLibraryItem(anyInt());

        ReturnLibraryItemService returnLibraryItemService = new ReturnLibraryItemService(serviceHandler, bookLibraryServiceManager, libraryRepository);
        returnLibraryItemService.returnBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(ReturnLibraryItemService.ALREADY_AVAILABLE_LIBRARY_ITEM));
    }
}


