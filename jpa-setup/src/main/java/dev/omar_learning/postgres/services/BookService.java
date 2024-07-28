package dev.omar_learning.postgres.services;

import dev.omar_learning.postgres.domain.Book;
import java.util.List;

public interface BookService {
  public Book findBookById(String isbn);

  public Book createBook(String isbn, Book book);

  public List<Book> findAll();
}

