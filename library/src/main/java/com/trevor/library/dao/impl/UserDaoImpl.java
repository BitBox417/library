package com.trevor.library.dao.impl;

import com.trevor.library.dao.UserDao;
import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.model.Inventory;
import com.trevor.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO User (User_Id, Phone_Number, Password, Registration_Time) VALUES (:userId, :phoneNumber, :password, :registrationTime)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("phoneNumber", user.getPhoneNumber())
                .addValue("password", user.getPassword())
                .addValue("registrationTime", LocalDateTime.now());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public User getUserById(String userId) {
        String sql = "SELECT * FROM User WHERE User_Id = :userId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, new UserRowMapper());
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE User SET Phone_Number = :phoneNumber, Password = :password, Last_Login_Time = :lastLoginTime WHERE User_Id = :userId";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("phoneNumber", user.getPhoneNumber())
                .addValue("password", user.getPassword())
                .addValue("lastLoginTime", LocalDateTime.now())
                .addValue("userId", user.getUserId());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public void deleteUser(String userId) {
        String sql = "DELETE FROM User WHERE User_Id = :userId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("userId", userId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public List<Inventory> getInventoriesByUserId(String userId) {
        String sql = "SELECT * FROM Inventory WHERE User_Id = :userId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.query(sql, parameters, new InventoryRowMapper());
    }

    public List<BorrowingRecord> getBorrowingRecordsByUserId(String userId) {
        String sql = "SELECT * FROM Borrowing_Record WHERE User_Id = :userId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.query(sql, parameters, new BorrowingRecordRowMapper());
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getString("User_Id"));
            user.setPhoneNumber(rs.getString("Phone_Number"));
            user.setPassword(rs.getString("Password"));
            user.setRegistrationTime(rs.getTimestamp("Registration_Time").toLocalDateTime());
            user.setLastLoginTime(rs.getTimestamp("Last_Login_Time").toLocalDateTime());
            return user;
        }
    }

    private static class InventoryRowMapper implements RowMapper<Inventory> {
        @Override
        public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {
            Inventory inventory = new Inventory();
            inventory.setInventoryId(rs.getString("Inventory_Id"));
            inventory.setIsbn(rs.getString("ISBN"));
            inventory.setStoreTime(rs.getTimestamp("Store_Time").toLocalDateTime());
            inventory.setStatus(rs.getString("Status"));
            return inventory;
        }
    }

    private static class BorrowingRecordRowMapper implements RowMapper<BorrowingRecord> {
        @Override
        public BorrowingRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            BorrowingRecord borrowingRecord = new BorrowingRecord();
            borrowingRecord.setIndexId(rs.getLong("Index_Id"));
            borrowingRecord.setUserId(rs.getString("User_Id"));
            borrowingRecord.setInventoryId(rs.getString("Inventory_Id"));
            borrowingRecord.setBorrowingTime(rs.getTimestamp("Borrowing_Time").toLocalDateTime());
            borrowingRecord.setReturnTime(rs.getTimestamp("Return_Time") != null ? rs.getTimestamp("Return_Time").toLocalDateTime() : null);
            return borrowingRecord;
        }
    }
}