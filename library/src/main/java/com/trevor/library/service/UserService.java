package com.trevor.library.service;

import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.model.Inventory;
import com.trevor.library.model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user);
    User login(String userId, String password);
    User getUserById(String userId);
    void updateUser(User user);
    void deleteUser(String userId);
    List<Inventory> getInventoriesByUserId(String userId);
    List<BorrowingRecord> getBorrowingRecordsByUserId(String userId);
}