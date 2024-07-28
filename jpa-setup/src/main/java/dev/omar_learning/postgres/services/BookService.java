package dev.omar_learning.postgres.services;

import dev.omar_learning.postgres.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
  public Book findBookById(String isbn);

  public Book createBook(String isbn, Book book);

  public List<Book> findAll();

  public Optional<Book> findOne(String isbn);
}

