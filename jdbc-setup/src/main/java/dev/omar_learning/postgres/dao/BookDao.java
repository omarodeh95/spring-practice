package dev.omar_learning.postgres.dao;

import dev.omar_learning.postgres.domain.Book;
import java.util.Optional;

public interface BookDao {
  void create(Book book);
  Optional<Book> findOne(String isbn);
}

