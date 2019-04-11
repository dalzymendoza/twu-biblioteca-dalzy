package com.twu.biblioteca;

import com.twu.biblioteca.errors.NonexistingBookError;
import com.twu.biblioteca.errors.UnavailableBookError;
import com.twu.biblioteca.repositories.BookRepository;
import com.twu.biblioteca.representations.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckoutServiceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setIn(System.in);
        System.setOut(System.out);
    }
//
//    @Test
//    public void shouldcheckoutAvailableBook() {
//        BookRepository bookRepository = mock(BookRepository.class);
//        List<Book> testListOf3Books = generateTestListOf3Books();
//        when(bookRepository.viewAllBooks()).thenReturn(testListOf3Books);
//
//        UIHandler uiHandler = new UIHandler();
//
//        CheckoutService service = new CheckoutService(bookRepository);
//        service.checkoutBook(1);
//        assertTrue(testListOf3Books.get(0).getAvailability());
//    }

//    @Test
//    public void checkoutExistingButUnavailableBook() throws NonexistingBookError, UnavailableBookError {
//        BookRepository bookRepository = mock(BookRepository.class);
//        doThrow(new UnavailableBookError()).when(bookRepository).checkoutBook(anyInt());
//        CheckoutService service = new CheckoutService(bookRepository);
//        service.checkoutBook(1);
//        assertThat(outContent.toString(), containsString(CheckoutService.UNAVAILABLE_MSG));
//    }
//
//    @Test
//    public void checkoutNonexistingBook() throws NonexistingBookError, UnavailableBookError  {
//        BookRepository bookRepository = mock(BookRepository.class);
//        doThrow(new NonexistingBookError()).when(bookRepository).checkoutBook(anyInt());
//        CheckoutService service = new CheckoutService(bookRepository);
//        service.checkoutBook(4);
//        assertThat(outContent.toString(), containsString(CheckoutService.NONEXISTING_MSG));
//    }
//
    private List<Book> generateTestListOf3Books() {
        List<Book> listOfBooks = new ArrayList<Book>();
        listOfBooks.add(new Book(1, "A Brief History of Time", "Stephen Hawking", Year.of(1988)));
        listOfBooks.add(new Book(2, "The Lion, the Witch and the Wardrobe", "C.S. Lewis", Year.of(1950)));
        listOfBooks.add(new Book(3, "Your Dream Life Starts Here", "Kristina Karlsson", Year.of(2018)));
        return listOfBooks;
    }
}
