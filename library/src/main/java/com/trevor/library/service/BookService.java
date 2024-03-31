package com.trevor.library.service;

import com.trevor.library.model.Book;
import com.trevor.library.model.Inventory;

import java.util.List;

public interface BookService {
    void createBook(Book book);
    Book getBookByIsbn(String isbn);
    void updateBook(Book book);
    void deleteBook(String isbn);
    Inventory getInventoryByIsbn(String isbn);
    List<Inventory> getInventoriesByIsbn(String isbn);
}