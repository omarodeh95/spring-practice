package dev.omar_learning.postgres.repositores;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.omar_learning.postgres.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

  Iterable<Author> ageLessThan(int age);

  @Query ("SELECT a FROM Author a WHERE a.age > ?1")
  Iterable<Author> findAuthorsWithAgeGreaterThan(int age);

}
