package com.twu.biblioteca;

import com.twu.biblioteca.exceptions.NonexistingLibraryItemException;
import com.twu.biblioteca.exceptions.UnavailableLibraryItemException;
import com.twu.biblioteca.repositories.LibraryRepository;
import com.twu.biblioteca.representations.Book;
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

    @Captor
    public ArgumentCaptor<String> uiHandlerPrintUserActionResponseCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void shouldPrintSuccessIfCheckingOutAvailableBookWithValidId() {
        LibraryRepository libraryRepository = mock(LibraryRepository.class);
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        LibraryService libraryServiceManager = mock(LibraryService.class);
        ViewLibraryItemService viewLibraryItemService = mock (ViewLibraryItemService.class);

        CheckoutLibraryItemService checkoutLibraryItemService = new CheckoutLibraryItemService(serviceHandler, libraryServiceManager,
                viewLibraryItemService, libraryRepository);
        checkoutLibraryItemService.checkoutLibraryItem(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutLibraryItemService.SUCCESS));
    }

    @Test
    public void shouldPrintNotAvailableIfCheckingOutUnavailableBook()
            throws NonexistingLibraryItemException, UnavailableLibraryItemException {
        LibraryRepository libraryRepository = mock(LibraryRepository.class);
        doThrow(new UnavailableLibraryItemException()).when(libraryRepository).checkoutLibraryItem(anyInt());
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        LibraryService libraryServiceManager = mock(LibraryService.class);
        ViewLibraryItemService viewLibraryItemService = mock (ViewLibraryItemService.class);

        CheckoutLibraryItemService checkoutLibraryItemService = new CheckoutLibraryItemService(serviceHandler, libraryServiceManager,
                viewLibraryItemService, libraryRepository);
        checkoutLibraryItemService.checkoutLibraryItem(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutLibraryItemService.NOT_AVAILABLE_LIBRARY_ITEM));
    }

    @Test
    public void shouldPrintNonExistingBookIfCheckingOutWithNonexistingBookId()
            throws NonexistingLibraryItemException, UnavailableLibraryItemException {
        LibraryRepository libraryRepository = mock(LibraryRepository.class);
        doThrow(new NonexistingLibraryItemException()).when(libraryRepository).checkoutLibraryItem(anyInt());
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        LibraryService libraryServiceManager = mock(LibraryService.class);
        ViewLibraryItemService viewLibraryItemService = mock (ViewLibraryItemService.class);

        CheckoutLibraryItemService checkoutLibraryItemService = new CheckoutLibraryItemService(serviceHandler, libraryServiceManager,
                viewLibraryItemService, libraryRepository);
        checkoutLibraryItemService.checkoutLibraryItem(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutLibraryItemService.NONEXISTING_LIBRARY_ITEM));
    }

    private List<Book> generateTestListOf3Books() {
        List<Book> listOfBooks = new ArrayList<Book>();
        listOfBooks.add(new Book(1, "A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        listOfBooks.add(new Book(2, "The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        listOfBooks.add(new Book(3, "Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
        return listOfBooks;
    }
}
