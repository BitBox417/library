package com.trevor.library.dao.impl;

import com.trevor.library.dao.InventoryDao;
import com.trevor.library.model.Book;
import com.trevor.library.model.BorrowingRecord;
import com.trevor.library.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InventoryDaoImpl implements InventoryDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InventoryDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createInventory(Inventory inventory) {
        String sql = "INSERT INTO Inventory (Inventory_Id, ISBN, Store_Time, Status) VALUES (:inventoryId, :isbn, :storeTime, :status)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("inventoryId", inventory.getInventoryId())
                .addValue("isbn", inventory.getIsbn())
                .addValue("storeTime", inventory.getStoreTime())
                .addValue("status", inventory.getStatus());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public Inventory getInventoryById(String inventoryId) {
        String sql = "SELECT * FROM Inventory WHERE Inventory_Id = :inventoryId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("inventoryId", inventoryId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, new InventoryRowMapper());
    }

    @Override
    public void updateInventory(Inventory inventory) {
        String sql = "UPDATE Inventory SET ISBN = :isbn, Store_Time = :storeTime, Status = :status WHERE Inventory_Id = :inventoryId";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("isbn", inventory.getIsbn())
                .addValue("storeTime", inventory.getStoreTime())
                .addValue("status", inventory.getStatus())
                .addValue("inventoryId", inventory.getInventoryId());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public void deleteInventory(String inventoryId) {
        String sql = "DELETE FROM Inventory WHERE Inventory_Id = :inventoryId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("inventoryId", inventoryId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public Book getBookByInventoryId(String inventoryId) {
        String sql = "SELECT b.* FROM Book b INNER JOIN Inventory i ON b.ISBN = i.ISBN WHERE i.Inventory_Id = :inventoryId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("inventoryId", inventoryId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, new BookRowMapper());
    }

    public List<BorrowingRecord> getBorrowingRecordsByInventoryId(String inventoryId) {
        String sql = "SELECT * FROM Borrowing_Record WHERE Inventory_Id = :inventoryId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("inventoryId", inventoryId);
        return namedParameterJdbcTemplate.query(sql, parameters, new BorrowingRecordRowMapper());
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

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setIsbn(rs.getString("ISBN"));
            book.setName(rs.getString("Name"));
            book.setAuthor(rs.getString("Author"));
            book.setIntroduction(rs.getString("Introduction"));
            return book;
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