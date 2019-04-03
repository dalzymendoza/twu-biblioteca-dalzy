package com.twu.biblioteca.representations;

import java.time.Year;

public class Book {

    private String title;
    private String author;
    private Year yearOfPublishing;

    public Book(String title, String author, Year yearOfPublishing) {
        this.title = title;
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
    }

    public String getTitle(){
        return title;
    }
}
