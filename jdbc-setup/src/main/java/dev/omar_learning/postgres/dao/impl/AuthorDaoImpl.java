package dev.omar_learning.postgres.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import dev.omar_learning.postgres.dao.AuthorDao;


public class AuthorDaoImpl implements AuthorDao {

  private final JdbcTemplate jdbcTemplate;

  public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}
