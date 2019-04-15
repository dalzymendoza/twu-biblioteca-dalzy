package com.twu.biblioteca.repositories;

import com.twu.biblioteca.errors.AvailableLibraryItemError;
import com.twu.biblioteca.errors.NonexistingLibraryItemError;
import com.twu.biblioteca.errors.UnavailableLibraryItemError;
import com.twu.biblioteca.representations.LibraryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LibraryRepository {

    private Map<Integer, LibraryItem> libraryItemMap;

    public LibraryRepository() {
        libraryItemMap = new HashMap<>();
    }

    public LibraryRepository(Map<Integer, LibraryItem> libraryItemMap) {
        this.libraryItemMap = libraryItemMap;
    }

    public List<LibraryItem> viewAllLibraryItems() {
        return new ArrayList<>(libraryItemMap.values());
    }

    public void checkoutLibraryItem(int id) throws NonexistingLibraryItemError, UnavailableLibraryItemError {
        if (libraryItemMap.containsKey(id)) {
            libraryItemMap.get(id).checkoutItem();
        }
        else {
            throw new NonexistingLibraryItemError();
        }
    }

    public LibraryItem getLibraryItem(int id) throws NonexistingLibraryItemError {
        if (libraryItemMap.containsKey(id)) {
            return libraryItemMap.get(id);
        }
        throw new NonexistingLibraryItemError();
    }

    public void returnLibraryItem(int id) throws NonexistingLibraryItemError, AvailableLibraryItemError {
        if (libraryItemMap.containsKey(id)) {
            LibraryItem libraryItem = libraryItemMap.get(id);
            if (libraryItem.getAvailability()) {
                throw new AvailableLibraryItemError();
            }
            else {
                libraryItem.returnItem();
            }

        }
        else {
            throw new NonexistingLibraryItemError();
        }
    }

    public void setLibraryItemMap(Map<Integer, LibraryItem> libraryItemMap) {
        this.libraryItemMap = libraryItemMap;
    }
}

