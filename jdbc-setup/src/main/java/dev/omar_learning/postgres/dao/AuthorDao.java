package dev.omar_learning.postgres.dao;

import dev.omar_learning.postgres.domain.Author;
import java.util.Optional;
import java.util.List;

public interface AuthorDao {
  void create(Author author);

  Optional<Author> findOne(Long id);

  List<Author> all();

  Optional<Author> update(Long authorId, Author newAuthor);

}
