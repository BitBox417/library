package com.trevor.library.service.impl;

import com.trevor.library.dao.BookDao;
import com.trevor.library.model.Book;
import com.trevor.library.model.Inventory;
import com.trevor.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void createBook(Book book) {
        bookDao.createBook(book);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookDao.getBookByIsbn(isbn);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBook(String isbn) {
        bookDao.deleteBook(isbn);
    }

    @Override
    public Inventory getInventoryByIsbn(String isbn) {
        return bookDao.getInventoryByIsbn(isbn);
    }

    @Override
    public List<Inventory> getInventoriesByIsbn(String isbn) {
        return bookDao.getInventoriesByIsbn(isbn);
    }
}
