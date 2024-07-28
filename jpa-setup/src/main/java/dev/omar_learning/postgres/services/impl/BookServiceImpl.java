package dev.omar_learning.postgres.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

import dev.omar_learning.postgres.domain.Book;
import dev.omar_learning.postgres.repositores.BookRepository;
import dev.omar_learning.postgres.services.BookService;


@Service
public class BookServiceImpl implements BookService {

  private BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Book findBookById(String isbn) {
    return bookRepository.findById(isbn).get();
  }

  @Override
  public Book save(Book book) {

    return bookRepository.save(book);
  }

  @Override
  public List<Book> findAll() {
    return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
      .collect(Collectors.toList());
  }

  @Override
  public Optional<Book> findOne(String isbn) {
    return bookRepository.findById(isbn);
  }

  @Override
  public boolean isExists(String isbn) {
    return bookRepository.existsById(isbn);
  }

  @Override
  public void delete(String bookIsbn) {
    bookRepository.deleteById(bookIsbn);
  }
}

