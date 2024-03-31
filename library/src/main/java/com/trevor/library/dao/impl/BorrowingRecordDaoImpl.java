package com.trevor.library.dao.impl;

import com.trevor.library.dao.BorrowingRecordDao;
import com.trevor.library.model.BorrowingRecord;
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
public class BorrowingRecordDaoImpl implements BorrowingRecordDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BorrowingRecordDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createBorrowingRecord(BorrowingRecord borrowingRecord) {
        String sql = "INSERT INTO Borrowing_Record (User_Id, Inventory_Id, Borrowing_Time) VALUES (:userId, :inventoryId, :borrowingTime)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", borrowingRecord.getUserId())
                .addValue("inventoryId", borrowingRecord.getInventoryId())
                .addValue("borrowingTime", borrowingRecord.getBorrowingTime());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public BorrowingRecord getBorrowingRecordById(Long indexId) {
        String sql = "SELECT * FROM Borrowing_Record WHERE Index_Id = :indexId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("indexId", indexId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, new BorrowingRecordRowMapper());
    }

    @Override
    public void updateBorrowingRecord(BorrowingRecord borrowingRecord) {
        String sql = "UPDATE Borrowing_Record SET User_Id = :userId, Inventory_Id = :inventoryId, Borrowing_Time = :borrowingTime, Return_Time = :returnTime WHERE Index_Id = :indexId";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", borrowingRecord.getUserId())
                .addValue("inventoryId", borrowingRecord.getInventoryId())
                .addValue("borrowingTime", borrowingRecord.getBorrowingTime())
                .addValue("returnTime", borrowingRecord.getReturnTime())
                .addValue("indexId", borrowingRecord.getIndexId());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public void deleteBorrowingRecord(Long indexId) {
        String sql = "DELETE FROM Borrowing_Record WHERE Index_Id = :indexId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("indexId", indexId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public List<BorrowingRecord> getBorrowingRecordsByInventoryId(String inventoryId) {
        String sql = "SELECT * FROM Borrowing_Record WHERE Inventory_Id = :inventoryId";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("inventoryId", inventoryId);
        return namedParameterJdbcTemplate.query(sql, parameters, new BorrowingRecordRowMapper());
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
