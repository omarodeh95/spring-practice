package dev.omar_learning.postgres.dao;

import dev.omar_learning.postgres.domain.Author;
import java.util.Optional;

public interface AuthorDao {
  void create(Author author);
  Optional<Author> findOne(Long id);
}
