package dev.omar_learning.postgres.controllers;

import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.dto.AuthorDto;
import dev.omar_learning.postgres.services.AuthorService;
import dev.omar_learning.postgres.mappers.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List; // Import List if needed
import java.util.stream.Collectors; // Import needed for stream operations

@RestController
public class AuthorsController {

  private final AuthorService authorService;
  private final Mapper<Author, AuthorDto> authorMapper;

  public AuthorsController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
  }

  @PostMapping(path = "/authors")
  public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
    Author author = authorMapper.mapFrom(authorDto);
    Author savedAuthor = authorService.createAuthor(author);
    return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
  }

  @GetMapping(path = "/authors")
  public ResponseEntity<List<AuthorDto>> listAuthors() {
    // Assuming authorService.findAll() returns Iterable<Author>
    List<AuthorDto> authorDtos = ((List<Author>) authorService.findAll()).stream()
        .map(authorMapper::mapTo)
        .collect(Collectors.toList());
    
    return new ResponseEntity<>(authorDtos, HttpStatus.OK);
  }
}

