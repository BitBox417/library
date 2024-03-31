package com.trevor.library.service;

import com.trevor.library.model.Book;
import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.model.Inventory;

import java.util.List;

public interface InventoryService {
    void createInventory(Inventory inventory);
    Inventory getInventoryById(String inventoryId);
    void updateInventory(Inventory inventory);
    void deleteInventory(String inventoryId);
    Book getBookByInventoryId(String inventoryId);
    List<BorrowingRecord> getBorrowingRecordsByInventoryId(String inventoryId);
    void borrowBook(String userId, String inventoryId);
    void returnBook(String userId, String inventoryId);
}