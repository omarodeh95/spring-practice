package dev.omar_learning.postgres.dao;

import dev.omar_learning.postgres.domain.Author;
import java.util.Optional;
import java.util.List;

public interface AuthorDao {
  void create(Author author);

  Optional<Author> findOne(Long id);

  List<Author> all();

  void update(Long authorId, Author newAuthor);

  void delete(Long authorId);

}
