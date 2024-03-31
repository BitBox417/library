package com.trevor.library.dao.impl;

import com.trevor.library.dao.BookDao;
import com.trevor.library.model.Book;
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
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createBook(Book book) {
        String sql = "INSERT INTO Book (ISBN, Name, Author, Introduction) VALUES (:isbn, :name, :author, :introduction)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("isbn", book.getIsbn())
                .addValue("name", book.getName())
                .addValue("author", book.getAuthor())
                .addValue("introduction", book.getIntroduction());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        String sql = "SELECT * FROM Book WHERE ISBN = :isbn";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("isbn", isbn);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, new BookRowMapper());
    }

    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE Book SET Name = :name, Author = :author, Introduction = :introduction WHERE ISBN = :isbn";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("author", book.getAuthor())
                .addValue("introduction", book.getIntroduction())
                .addValue("isbn", book.getIsbn());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public void deleteBook(String isbn) {
        String sql = "DELETE FROM Book WHERE ISBN = :isbn";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("isbn", isbn);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public Inventory getInventoryByIsbn(String isbn) {
        String sql = "SELECT * FROM Inventory WHERE ISBN = :isbn";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("isbn", isbn);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, new InventoryRowMapper());
    }

    public List<Inventory> getInventoriesByIsbn(String isbn) {
        String sql = "SELECT * FROM Inventory WHERE ISBN = :isbn";
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("isbn", isbn);
        return namedParameterJdbcTemplate.query(sql, parameters, new InventoryRowMapper());
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
}