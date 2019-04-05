package com.twu.biblioteca.representations;

import com.twu.biblioteca.errors.AvailableBookError;
import com.twu.biblioteca.errors.UnavailableBookError;

import java.time.Year;

public class Book {

    private int id;
    private String title;
    private String author;
    private Year yearOfPublishing;
    private boolean availability;

    public Book(int id, String title, String author, Year yearOfPublishing) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
        this.availability = true;
    }

    public String getTitle(){
        return title;
    }

    public String getBookOptionPrintFormat() {
        return "[" + id + "] " + title;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void checkout() throws UnavailableBookError {
        if(availability) {
            this.availability = false;
        }
        else {
            throw new UnavailableBookError();
        }
    }

    public void returnBook() throws AvailableBookError {
        if (availability) {
            throw new AvailableBookError();
        }
        else {
            this.availability = true;
        }
    }
}
