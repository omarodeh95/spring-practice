package dev.omar_learning.postgres.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import dev.omar_learning.postgres.dao.AuthorDao;
import dev.omar_learning.postgres.domain.Author;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.List;


@Component
public class AuthorDaoImpl implements AuthorDao {

  private final JdbcTemplate jdbcTemplate;

  public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void create(Author author) {
    jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)", author.getId(), author.getName(), author.getAge());
  }

  @Override
  public Optional<Author> findOne(Long authorId) {
    List<Author> results = jdbcTemplate.query("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1", new AuthorRowMapper(), authorId);

    return results.stream().findFirst();
  }

  @Override
  public List<Author> all() {
    List<Author> result = jdbcTemplate.query(
        "SELECT id, name, age FROM authors",
        new AuthorRowMapper()
        );

    return result;
  }

  @Override
  public Optional<Author> update(Long authorId, Author newAuthor) {
    List<Author> result = jdbcTemplate.query(
        "UPDATE authors SET id = ?, name = ?, age = ? WHERE authors.id = ?", 
        new AuthorRowMapper(),
        newAuthor.getId(),
        newAuthor.getName(),
        newAuthor.getAge(),
        authorId
        );

    Optional<Author> updateAuthor = result.stream().findFirst();
    return updateAuthor;
  }


  public static class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
      return Author.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("name"))
        .age(rs.getInt("age"))
        .build();
    }
  }
}
