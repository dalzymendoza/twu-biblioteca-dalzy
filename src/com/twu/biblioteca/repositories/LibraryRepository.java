package com.twu.biblioteca.repositories;

import com.twu.biblioteca.exceptions.AvailableLibraryItemException;
import com.twu.biblioteca.exceptions.NonexistingLibraryItemException;
import com.twu.biblioteca.exceptions.UnavailableLibraryItemException;
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

    public void checkoutLibraryItem(int id) throws NonexistingLibraryItemException, UnavailableLibraryItemException {
        if (libraryItemMap.containsKey(id)) {
            libraryItemMap.get(id).checkoutItem();
        }
        else {
            throw new NonexistingLibraryItemException();
        }
    }

    public LibraryItem getLibraryItem(int id) throws NonexistingLibraryItemException {
        if (libraryItemMap.containsKey(id)) {
            return libraryItemMap.get(id);
        }
        throw new NonexistingLibraryItemException();
    }

    public void returnLibraryItem(int id) throws NonexistingLibraryItemException, AvailableLibraryItemException {
        if (libraryItemMap.containsKey(id)) {
            LibraryItem libraryItem = libraryItemMap.get(id);
            if (libraryItem.getAvailability()) {
                throw new AvailableLibraryItemException();
            }
            else {
                libraryItem.returnItem();
            }

        }
        else {
            throw new NonexistingLibraryItemException();
        }
    }

    public void setLibraryItemMap(Map<Integer, LibraryItem> libraryItemMap) {
        this.libraryItemMap = libraryItemMap;
    }
}

