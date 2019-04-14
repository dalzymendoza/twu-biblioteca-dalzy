package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingLibraryItemError;
import com.twu.biblioteca.errors.UnavailableLibraryItemError;
import com.twu.biblioteca.repositories.BookRepository;
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

public class CheckoutBookServiceTest {

    @Captor
    public ArgumentCaptor<String> uiHandlerPrintUserActionResponseCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void shouldPrintSuccessIfCheckingOutAvailableBookWithValidId() {
        BookRepository bookRepository = mock(BookRepository.class);
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        BookLibraryService bookLibraryServiceManager = mock(BookLibraryService.class);
        ViewBookService viewBookService = mock (ViewBookService.class);

        CheckoutBookService checkoutBookService = new CheckoutBookService(serviceHandler, bookLibraryServiceManager,
                viewBookService, bookRepository);
        checkoutBookService.checkoutBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutBookService.SUCCESS));
    }

    @Test
    public void shouldPrintNotAvailableIfCheckingOutUnavailableBook()
            throws NonexistingLibraryItemError, UnavailableLibraryItemError {
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new UnavailableLibraryItemError()).when(bookRepository).checkoutBook(anyInt());
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        BookLibraryService bookLibraryServiceManager = mock(BookLibraryService.class);
        ViewBookService viewBookService = mock (ViewBookService.class);

        CheckoutBookService checkoutBookService = new CheckoutBookService(serviceHandler, bookLibraryServiceManager,
                viewBookService, bookRepository);
        checkoutBookService.checkoutBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutBookService.NOT_AVAILABLE_BOOK));
    }

    @Test
    public void shouldPrintNonExistingBookIfCheckingOutWithNonexistingBookId()
            throws NonexistingLibraryItemError, UnavailableLibraryItemError {
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new NonexistingLibraryItemError()).when(bookRepository).checkoutBook(anyInt());
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        BookLibraryService bookLibraryServiceManager = mock(BookLibraryService.class);
        ViewBookService viewBookService = mock (ViewBookService.class);

        CheckoutBookService checkoutBookService = new CheckoutBookService(serviceHandler, bookLibraryServiceManager,
                viewBookService, bookRepository);
        checkoutBookService.checkoutBook(1);
        verify(serviceHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutBookService.NONEXISTING_BOOK));
    }

    private List<Book> generateTestListOf3Books() {
        List<Book> listOfBooks = new ArrayList<Book>();
        listOfBooks.add(new Book(1, "A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        listOfBooks.add(new Book(2, "The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        listOfBooks.add(new Book(3, "Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
        return listOfBooks;
    }
}
