package dev.omar_learning.postgres.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import dev.omar_learning.postgres.dao.BookDao;

public class BookDaoImpl implements BookDao {

  private final JdbcTemplate jdbcTemplate;

  public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}
