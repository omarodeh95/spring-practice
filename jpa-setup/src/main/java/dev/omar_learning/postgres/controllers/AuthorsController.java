package dev.omar_learning.postgres.controllers;

import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.dto.AuthorDto;
import dev.omar_learning.postgres.services.AuthorService;
import dev.omar_learning.postgres.mappers.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List; // Import List if needed
import java.util.Optional;
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
    Author savedAuthor = authorService.save(author);
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

  @GetMapping(path = "/authors/{authorId}")
  public ResponseEntity<AuthorDto> showAuthor(@PathVariable("authorId") Long authorId) {
    Optional<Author> fetchedAuthor = authorService.findOne(authorId);

    return fetchedAuthor.map(author -> {

      AuthorDto authorDto = authorMapper.mapTo(author);

      return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping(path = "/authors/{authorId}")
  public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("authorId") Long authorId,  @RequestBody AuthorDto authorDto) {
    if (!authorService.isExists(authorId)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      authorDto.setId(authorId);

      Author author = authorMapper.mapFrom(authorDto);

      Author updatedAuthor = authorService.save(author);

      AuthorDto updatedAuthorDto = authorMapper.mapTo(updatedAuthor);

      return new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK);
    }
  }
}

