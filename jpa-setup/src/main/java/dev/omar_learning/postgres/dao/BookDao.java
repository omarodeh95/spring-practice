package dev.omar_learning.postgres.dao;

import dev.omar_learning.postgres.domain.Book;
import java.util.Optional;
import java.util.List;

public interface BookDao {
  void create(Book book);

  Optional<Book> findOne(String isbn);

  List<Book> all();

  void update(String isbn, Book newBook);

  void delete(String isbn);
}

