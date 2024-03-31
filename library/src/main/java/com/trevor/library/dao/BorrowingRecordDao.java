package com.trevor.library.dao;

import com.trevor.library.model.BorrowingRecord;

import java.util.List;

public interface BorrowingRecordDao {
    void createBorrowingRecord(BorrowingRecord borrowingRecord);
    BorrowingRecord getBorrowingRecordById(Long indexId);
    void updateBorrowingRecord(BorrowingRecord borrowingRecord);
    void deleteBorrowingRecord(Long indexId);
    List<BorrowingRecord> getBorrowingRecordsByInventoryId(String inventoryId);
}