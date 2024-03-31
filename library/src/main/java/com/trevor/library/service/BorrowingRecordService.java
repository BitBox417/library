package com.trevor.library.service;

import com.trevor.library.model.BorrowingRecord;

import java.util.List;

public interface BorrowingRecordService {
    void createBorrowingRecord(BorrowingRecord borrowingRecord);
    BorrowingRecord getBorrowingRecordById(Long indexId);
    void updateBorrowingRecord(BorrowingRecord borrowingRecord);
    void deleteBorrowingRecord(Long indexId);
    List<BorrowingRecord> getBorrowingRecordsByInventoryId(String inventoryId);
}
