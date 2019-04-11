package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.errors.UnavailableBookError;
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

public class CheckoutServiceTest {

    @Captor
    public ArgumentCaptor<String> uiHandlerPrintUserActionResponseCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void shouldPrintSuccessIfCheckingOutAvailableBookWithValidId() {
        BookRepository bookRepository = mock(BookRepository.class);
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        ViewService viewService = mock (ViewService.class);

        CheckoutService checkoutService = new CheckoutService(uiHandler, bookScreenManager,
                                                      viewService, bookRepository);
        checkoutService.checkoutBook(1);
        verify(uiHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutService.SUCCESS));
    }

    @Test
    public void shouldPrintNotAvailableIfCheckingOutUnavailableBook()
            throws NonexistingBookError, UnavailableBookError {
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new UnavailableBookError()).when(bookRepository).checkoutBook(anyInt());
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        ViewService viewService = mock (ViewService.class);

        CheckoutService checkoutService = new CheckoutService(uiHandler, bookScreenManager,
                viewService, bookRepository);
        checkoutService.checkoutBook(1);
        verify(uiHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutService.NOT_AVAILABLE_BOOK));
    }

    @Test
    public void shouldPrintNonExistingBookIfCheckingOutWithNonexistingBookId()
            throws NonexistingBookError, UnavailableBookError  {
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new NonexistingBookError()).when(bookRepository).checkoutBook(anyInt());
        UIHandler uiHandler = mock(UIHandler.class);
        BookScreenManager bookScreenManager = mock(BookScreenManager.class);
        ViewService viewService = mock (ViewService.class);

        CheckoutService checkoutService = new CheckoutService(uiHandler, bookScreenManager,
                viewService, bookRepository);
        checkoutService.checkoutBook(1);
        verify(uiHandler).printUserActionRespone(uiHandlerPrintUserActionResponseCaptor.capture());
        assertThat(uiHandlerPrintUserActionResponseCaptor.getValue(), containsString(CheckoutService.NONEXISTING_BOOK));
    }

    private List<Book> generateTestListOf3Books() {
        List<Book> listOfBooks = new ArrayList<Book>();
        listOfBooks.add(new Book(1, "A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        listOfBooks.add(new Book(2, "The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        listOfBooks.add(new Book(3, "Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
        return listOfBooks;
    }
}
