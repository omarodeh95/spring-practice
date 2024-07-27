package dev.omar_learning.postgres.services.impl;

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
}
