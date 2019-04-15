package com.twu.biblioteca;

import com.twu.biblioteca.exceptions.NonexistingLibraryItemException;
import com.twu.biblioteca.exceptions.UnavailableLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;
import com.twu.biblioteca.representations.Book;
import com.twu.biblioteca.representations.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class CheckoutLibraryItemServiceTest {

    private LibraryRepository libraryRepository;
    private ServiceHandler serviceHandler;
    private LibraryService libraryService;
    private ViewLibraryItemService viewLibraryItemService;
    private CheckoutLibraryItemService checkoutLibraryItemService;

    @Captor
    public ArgumentCaptor<String> uiHandlerPrintUserActionResponseCaptor = ArgumentCaptor.forClass(String.class);

    @Before
    public void setUpLibraryRepoServiceHandlerViewLibraryService() {
        libraryRepository = mock(LibraryRepository.class);
        serviceHandler = mock(ServiceHandler.class);
        libraryService = mock(LibraryService.class);
        viewLibraryItemService = mock (ViewLibraryItemService.class);
        checkoutLibraryItemService = new CheckoutLibraryItemService(serviceHandler, libraryService,
                                                                    viewLibraryItemService, libraryRepository);
    }

    @Test
    public void shouldPrintSuccessIfCheckingOutAvailableBookWithValidId() {
        checkoutLibraryItemService.checkoutLibraryItem(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutLibraryItemService.SUCCESS));
    }

    @Test
    public void shouldPrintNotAvailableIfCheckingOutUnavailableBook()
            throws NonexistingLibraryItemException, UnavailableLibraryItemException {
        doThrow(new UnavailableLibraryItemException()).when(libraryRepository).checkoutLibraryItem(anyInt(), any(User.class));
        checkoutLibraryItemService.checkoutLibraryItem(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutLibraryItemService.NOT_AVAILABLE_LIBRARY_ITEM));
    }

    @Test
    public void shouldPrintNonExistingBookIfCheckingOutWithNonexistingBookId()
            throws NonexistingLibraryItemException, UnavailableLibraryItemException {
        doThrow(new NonexistingLibraryItemException()).when(libraryRepository).checkoutLibraryItem(anyInt(), any(User.class));
        checkoutLibraryItemService.checkoutLibraryItem(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutLibraryItemService.NONEXISTING_LIBRARY_ITEM));
    }
}
