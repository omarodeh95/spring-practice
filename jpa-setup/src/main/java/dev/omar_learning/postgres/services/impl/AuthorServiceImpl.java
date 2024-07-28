package dev.omar_learning.postgres.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.repositores.AuthorRepository;
import dev.omar_learning.postgres.services.AuthorService;


@Service
public class AuthorServiceImpl implements AuthorService {

  private AuthorRepository authorRepository;

  public AuthorServiceImpl(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  public Author createAuthor(Author author) {
    return authorRepository.save(author);
  }

  @Override
  public List<Author> findAll() {
    return StreamSupport.stream(authorRepository .findAll() .spliterator(), false)
      .collect(Collectors.toList());
  }

  @Override
  public Author findOne(Long authorId) {
    return authorRepository.findById(authorId).get();
  }
}
