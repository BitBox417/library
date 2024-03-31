package com.trevor.library.service.impl;

import com.trevor.library.dao.BorrowingRecordDao;
import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.service.BorrowingRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
    private final BorrowingRecordDao borrowingRecordDao;

    public BorrowingRecordServiceImpl(BorrowingRecordDao borrowingRecordDao) {
        this.borrowingRecordDao = borrowingRecordDao;
    }

    @Override
    public void createBorrowingRecord(BorrowingRecord borrowingRecord) {
        borrowingRecordDao.createBorrowingRecord(borrowingRecord);
    }

    @Override
    public BorrowingRecord getBorrowingRecordById(Long indexId) {
        return borrowingRecordDao.getBorrowingRecordById(indexId);
    }

    @Override
    public void updateBorrowingRecord(BorrowingRecord borrowingRecord) {
        borrowingRecordDao.updateBorrowingRecord(borrowingRecord);
    }

    @Override
    public void deleteBorrowingRecord(Long indexId) {
        borrowingRecordDao.deleteBorrowingRecord(indexId);
    }

    @Override
    public List<BorrowingRecord> getBorrowingRecordsByInventoryId(String inventoryId) {
        return borrowingRecordDao.getBorrowingRecordsByInventoryId(inventoryId);
    }
}
