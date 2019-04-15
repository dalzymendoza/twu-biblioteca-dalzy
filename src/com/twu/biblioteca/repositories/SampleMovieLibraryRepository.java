package com.twu.biblioteca.repositories;

import com.twu.biblioteca.representations.LibraryItem;
import com.twu.biblioteca.representations.Movie;
import com.twu.biblioteca.representations.Rating;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class SampleMovieLibraryRepository extends LibraryRepository{

    public SampleMovieLibraryRepository() {
        super.setLibraryItemMap(generateSampleMapOf3Movies());

    }

    private Map<Integer, LibraryItem> generateSampleMapOf3Movies() {
        Map <Integer, LibraryItem> movies = new HashMap<>();
        movies.put(1, new Movie(1, "Guardians of the Galaxy Vol. 2", "James Gunn", Year.of(2017), new Rating(8)));
        movies.put(2, new Movie(2, "A Beautiful Mind", "Ron Howard", Year.of(2002), new Rating(8)));
        movies.put(3, new Movie(3, "How To Train Your Dragon: The Hidden World", "Dean DeBlois", Year.of(2019), new Rating(8)));
        return movies;
    }
}
