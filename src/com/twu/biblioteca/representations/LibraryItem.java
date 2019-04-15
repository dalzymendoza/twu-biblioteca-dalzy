package com.twu.biblioteca.representations;

import com.twu.biblioteca.exceptions.AvailableLibraryItemException;
import com.twu.biblioteca.exceptions.UnavailableLibraryItemException;

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

    public void checkoutItem() throws UnavailableLibraryItemException {
        if(availability) {
            this.availability = false;
        }
        else {
            throw new UnavailableLibraryItemException();
        }
    }

    public void returnItem() throws AvailableLibraryItemException {
        if (availability) {
            throw new AvailableLibraryItemException();
        }
        else {
            this.availability = true;
        }
    }
}
