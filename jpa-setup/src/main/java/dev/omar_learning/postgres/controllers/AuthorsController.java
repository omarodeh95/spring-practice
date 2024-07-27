package dev.omar_learning.postgres.controllers;

import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.dto.AuthorDto;
import dev.omar_learning.postgres.services.AuthorService;

import dev.omar_learning.postgres.mappers.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class AuthorsController {

  private AuthorService authorService;
  private Mapper<Author, AuthorDto> authorMapper;

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
}
