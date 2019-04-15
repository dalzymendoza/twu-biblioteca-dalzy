package com.twu.biblioteca.representations;

import java.time.Year;

public class Movie extends LibraryItem {

    private String director;
    private Year yearReleased;
    private Rating rating;


    public Movie(int id, String title, String director, Year yearReleased, Rating rating) {
        super(id, title);
        this.director = director;
        this.yearReleased = yearReleased;
        this.rating = rating;
    }

    @Override
    public String getExtraDetailsPrintFormat() {
        return "Rating: " + rating + "\n" +
               "Year: " + yearReleased + "\n" +
               "Director: " + director + "\n";
    }

    public Rating getRating() {
        return rating;
    }

}
