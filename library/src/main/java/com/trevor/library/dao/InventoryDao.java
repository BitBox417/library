package com.trevor.library.dao;

import com.trevor.library.model.Book;
import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.model.Inventory;

import java.util.List;

public interface InventoryDao {
    void createInventory(Inventory inventory);
    Inventory getInventoryById(String inventoryId);
    void updateInventory(Inventory inventory);
    void deleteInventory(String inventoryId);
    Book getBookByInventoryId(String inventoryId);
    List<BorrowingRecord> getBorrowingRecordsByInventoryId(String inventoryId);
}