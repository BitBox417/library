package com.trevor.library.service.impl;

import com.trevor.library.dao.InventoryDao;
import com.trevor.library.model.Book;
import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.model.Inventory;
import com.trevor.library.service.BorrowingRecordService;
import com.trevor.library.service.InventoryService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryDao inventoryDao;
    private final BorrowingRecordService borrowingRecordService;

    public InventoryServiceImpl(InventoryDao inventoryDao, BorrowingRecordService borrowingRecordService) {
        this.inventoryDao = inventoryDao;
        this.borrowingRecordService = borrowingRecordService;
    }

    @Override
    public void createInventory(Inventory inventory) {
        inventoryDao.createInventory(inventory);
    }

    @Override
    public Inventory getInventoryById(String inventoryId) {
        return inventoryDao.getInventoryById(inventoryId);
    }

    @Override
    public void updateInventory(Inventory inventory) {
        inventoryDao.updateInventory(inventory);
    }

    @Override
    public void deleteInventory(String inventoryId) {
        inventoryDao.deleteInventory(inventoryId);
    }

    @Override
    public Book getBookByInventoryId(String inventoryId) {
        return inventoryDao.getBookByInventoryId(inventoryId);
    }

    @Override
    public List<BorrowingRecord> getBorrowingRecordsByInventoryId(String inventoryId) {
        return inventoryDao.getBorrowingRecordsByInventoryId(inventoryId);
    }

    @Override
    public void borrowBook(String userId, String inventoryId) {
        Inventory inventory = inventoryDao.getInventoryById(inventoryId);
        if (inventory != null && inventory.getStatus().equals("Available")) {
            inventory.setStatus("Borrowed");
            inventoryDao.updateInventory(inventory);

            BorrowingRecord borrowingRecord = new BorrowingRecord();
            borrowingRecord.setUserId(userId);
            borrowingRecord.setInventoryId(inventoryId);
            borrowingRecord.setBorrowingTime(LocalDateTime.now());
            borrowingRecordService.createBorrowingRecord(borrowingRecord);
        } else {
            throw new IllegalStateException("Book is not available for borrowing.");
        }
    }

    @Override
    public void returnBook(String userId, String inventoryId) {
        Inventory inventory = inventoryDao.getInventoryById(inventoryId);
        if (inventory != null && inventory.getStatus().equals("Borrowed")) {
            inventory.setStatus("Available");
            inventoryDao.updateInventory(inventory);

            BorrowingRecord borrowingRecord = borrowingRecordService.getBorrowingRecordsByInventoryId(inventoryId)
                    .stream()
                    .filter(record -> record.getUserId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No borrowing record found."));

            borrowingRecord.setReturnTime(LocalDateTime.now());
            borrowingRecordService.updateBorrowingRecord(borrowingRecord);
        } else {
            throw new IllegalStateException("Book is not borrowed or not found.");
        }
    }
}