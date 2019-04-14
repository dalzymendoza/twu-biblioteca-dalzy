package com.twu.biblioteca.representations;

import com.twu.biblioteca.errors.AvailableLibraryItemError;
import com.twu.biblioteca.errors.UnavailableLibraryItemError;

public abstract class LibraryItem {

    private int id;
    private String title;
    private boolean availability;

    public LibraryItem(int id, String title) {
        this.id = id;
        this.title = title;
        this.availability = true;
    }

    public abstract String getExtraDetailsPrintFormat();

    public String getLibraryItemOptionPrintFormat() {
        return "[" + id + "] " + title;
    }

    public boolean getAvailability() {
        return availability;
    }

    public String getTitle(){
        return title;
    }

    public int getId() {
        return id;
    }

    public void checkoutItem() throws UnavailableLibraryItemError {
        if(availability) {
            this.availability = false;
        }
        else {
            throw new UnavailableLibraryItemError();
        }
    }

    public void returnItem() throws AvailableLibraryItemError {
        if (availability) {
            throw new AvailableLibraryItemError();
        }
        else {
            this.availability = true;
        }
    }
}
