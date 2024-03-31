package com.trevor.library.service.impl;

import com.trevor.library.dao.UserDao;
import com.trevor.library.exception.AuthenticationException;
import com.trevor.library.exception.UserNotFoundException;
import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.model.Inventory;
import com.trevor.library.model.User;
import com.trevor.library.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void registerUser(User user) {
        // 檢查使用者資料是否合法
        if (isValidUser(user)) {
            // 密碼加密
            String encryptedPassword = encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);
            userDao.createUser(user);
        } else {
            throw new IllegalArgumentException("Invalid user data.");
        }
    }

    @Override
    public User login(String userId, String password) {
        User user = userDao.getUserById(userId);
        if (user != null && user.getPassword().equals(encryptPassword(password))) {
            // 更新最後登入時間
            user.setLastLoginTime(LocalDateTime.now());
            userDao.updateUser(user);
            return user;
        } else {
            throw new AuthenticationException("Invalid userId or password.");
        }
    }

    @Override
    public User getUserById(String userId) {
        User user = userDao.getUserById(userId);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User not found with userId: " + userId);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User existingUser = userDao.getUserById(user.getUserId());
        if (existingUser != null) {
            // 更新使用者資訊
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setPassword(encryptPassword(user.getPassword()));
            userDao.updateUser(existingUser);
        } else {
            throw new UserNotFoundException("User not found with userId: " + user.getUserId());
        }
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        User existingUser = userDao.getUserById(userId);
        if (existingUser != null) {
            userDao.deleteUser(userId);
        } else {
            throw new UserNotFoundException("User not found with userId: " + userId);
        }
    }

    @Override
    public List<Inventory> getInventoriesByUserId(String userId) {
        User existingUser = userDao.getUserById(userId);
        if (existingUser != null) {
            return userDao.getInventoriesByUserId(userId);
        } else {
            throw new UserNotFoundException("User not found with userId: " + userId);
        }
    }

    @Override
    public List<BorrowingRecord> getBorrowingRecordsByUserId(String userId) {
        User existingUser = userDao.getUserById(userId);
        if (existingUser != null) {
            return userDao.getBorrowingRecordsByUserId(userId);
        } else {
            throw new UserNotFoundException("User not found with userId: " + userId);
        }
    }

    private boolean isValidUser(User user) {
        // 檢查使用者資料的合法性,例如用戶名和密碼的長度、格式等
        // 返回 true 表示合法,返回 false 表示不合法
        // 具體的驗證邏輯需要根據實際需求來實現
        return true;
    }

    private String encryptPassword(String password) {
        // 使用加密算法對密碼進行加密
        // 這裡僅為示例,實際應用中需要使用安全的加密算法,如 bcrypt、PBKDF2 等
        return password + "_encrypted";
    }
}