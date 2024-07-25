package dev.omar_learning.postgres.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import dev.omar_learning.postgres.dao.BookDao;
import dev.omar_learning.postgres.domain.Book;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

  private final JdbcTemplate jdbcTemplate;

  public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void create(Book book) {
    jdbcTemplate.update("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)", book.getIsbn(), book.getTitle(), book.getAuthorId());
  }


  @Override
  public Optional<Book> findOne(String isbn) {
    List<Book> result = jdbcTemplate.query("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1", new BookRowMapper(), isbn); 

    return result.stream().findFirst();
  }

  @Override
  public List<Book> all() {
    List<Book> result = jdbcTemplate.query(
        "SELECT isbn, title, author_id FROM books",
        new BookRowMapper()
        );

    return result;
  }

  public static class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
      return Book.builder()
        .isbn(rs.getString("isbn"))
        .title(rs.getString("title"))
        .authorId(rs.getLong("author_id"))
        .build();
    }
  }

}
