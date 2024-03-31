package com.trevor.library.dao;

import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.model.Inventory;
import com.trevor.library.model.User;

import java.util.List;

public interface UserDao {
    void createUser(User user);
    User getUserById(String userId);
    void updateUser(User user);
    void deleteUser(String userId);
    List<Inventory> getInventoriesByUserId(String userId);
    List<BorrowingRecord> getBorrowingRecordsByUserId(String userId);
}