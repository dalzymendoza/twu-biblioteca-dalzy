package com.twu.biblioteca.representations;

public class Rating {
    private int value;
    public static final String PRINT_UNRATED = "Rating: Unrated";

    public Rating() {
        value = 0;
    }

    public Rating(int value) {
        if(isValidRatingValue(value)) {
            this.value = value;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void setValue(int value) throws IllegalArgumentException{
        if (isValidRatingValue(value)) {
            this.value = value;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public String toString() {
        if(value < 1) {
            return PRINT_UNRATED;
        }
        return "Rating: " + value;
    }

    private boolean isValidRatingValue(int value) {
        if (1 <= value && value <= 10) {
            return true;
        }
        return false;
    }
}
