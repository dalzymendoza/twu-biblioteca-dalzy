package com.twu.biblioteca.representations;

import java.time.Year;

public class Book extends LibraryItem{

    private String author;
    private Year yearOfPublishing;

    public Book(int id, String title, String author, Year yearOfPublishing) {
        super(id, title);
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
    }

    @Override
    public String getExtraDetailsPrintFormat() {
        return "Author: " + author + "\n" + "Year: " + yearOfPublishing.toString() + "\n";
    }

}
