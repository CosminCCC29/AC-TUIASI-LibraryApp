package com.example.app.repositories.mappers;

import com.example.app.models.dbentities.BookEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<BookEntity> {
    @Override
    public BookEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setISBN(rs.getString("isbn"));
        bookEntity.setTitle(rs.getString("title"));
        bookEntity.setGenre(rs.getString("genre"));
        bookEntity.setPublisher(rs.getString("publisher"));
        bookEntity.setYear(rs.getInt("year"));
        bookEntity.setPrice(rs.getFloat("price"));
        bookEntity.setDescription(rs.getString("description"));
        bookEntity.setStock(rs.getInt("stock"));
        return bookEntity;
    }
}
