package dev.omar_learning.postgres;

import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.domain.Book;

public class TestDataUtil {

  // Should we make this an abstract class?
  private TestDataUtil(){
  }

  public static Author buildTestAuthor() {
    return Author.builder()
      .id(1L)
      .name("Omar Odeh")
      .age(90)
      .build();
  }

  public static Book buildTestBook() {
    return Book.builder()
      .isbn("123-456-789")
      .title("Lord of The Ring")
      .authorId(1L)
      .build();
  }
}

