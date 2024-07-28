package dev.omar_learning.postgres.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PutMapping("/books/{isbn}")
  public ResponseEntity<BookDto> updateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
    Book newBook = bookMapper.mapFrom(bookDto);

    Book savedBook = bookService.createBook(isbn, newBook);

    return new ResponseEntity<>(bookMapper.mapTo(savedBook), HttpStatus.CREATED);
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
    Book fetchedBook = bookService.findOne(isbn);

    return new ResponseEntity<>(bookMapper.mapTo(fetchedBook), HttpStatus.OK);
  }
}
