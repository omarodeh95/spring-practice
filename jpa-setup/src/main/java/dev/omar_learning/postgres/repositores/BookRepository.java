package dev.omar_learning.postgres.repositores;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.omar_learning.postgres.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String>{
}

