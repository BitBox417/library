package com.trevor.library.dao;

import com.trevor.library.model.Book;
import com.trevor.library.model.Inventory;

import java.util.List;

public interface BookDao {
    void createBook(Book book);
    Book getBookByIsbn(String isbn);
    void updateBook(Book book);
    void deleteBook(String isbn);
    Inventory getInventoryByIsbn(String isbn);
    List<Inventory> getInventoriesByIsbn(String isbn);
}