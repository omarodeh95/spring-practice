package dev.omar_learning.postgres.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.omar_learning.postgres.domain.Book;
import dev.omar_learning.postgres.dto.BookDto;
import dev.omar_learning.postgres.mappers.Mapper;
import dev.omar_learning.postgres.services.BookService;

@RestController
public class BooksController {

  private BookService bookService;
  private Mapper<Book, BookDto> bookMapper;

  public BooksController(BookService bookService, Mapper<Book, BookDto> bookMapper) {
    this.bookService = bookService;
    this.bookMapper = bookMapper;
  }

  @GetMapping(path = "/books")
  public ResponseEntity<List<BookDto>> listBooks() {
    List<BookDto> bookDtos = ((List<Book>) bookService.findAll()).stream()
        .map(bookMapper::mapTo)
        .collect(Collectors.toList());
    
    return new ResponseEntity<>(bookDtos, HttpStatus.OK);
  }

  @GetMapping(path = "/books/{isbn}")
  public ResponseEntity<BookDto> showBook(@PathVariable("isbn") String isbn) {

    Optional<Book> fetchedBook = bookService.findOne(isbn);

    return fetchedBook.map(book -> {

      BookDto bookDto = bookMapper.mapTo(book);

      return new ResponseEntity<>(bookDto, HttpStatus.OK);

    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping(path = "/books/{bookIsbn}")
  public ResponseEntity<BookDto> updatebook(@PathVariable("bookIsbn") String bookIsbn,  @RequestBody BookDto bookDto) {
    bookDto.setIsbn(bookIsbn);

    if (!bookService.isExists(bookIsbn)) {
      Book newBook = bookMapper.mapFrom(bookDto);

      Book savedBook = bookService.save(newBook);

      return new ResponseEntity<>(bookMapper.mapTo(savedBook), HttpStatus.CREATED);
    } else {
      Book book = bookMapper.mapFrom(bookDto);

      Book updatedbook = bookService.save(book);

      BookDto updatedBookDto = bookMapper.mapTo(updatedbook);

      return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
    }
  }

  @DeleteMapping(path = "/books/{bookIsbn}")
  public ResponseEntity deleteBook(@PathVariable("bookIsbn") String bookIsbn) {
    bookService.delete(bookIsbn);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
