package dev.omar_learning.postgres.services;

import java.util.List;

import dev.omar_learning.postgres.domain.Author;

public interface AuthorService {
  public Author createAuthor(Author author);

  public List<Author> findAll();

  public Author findOne(Long authorId);
}
