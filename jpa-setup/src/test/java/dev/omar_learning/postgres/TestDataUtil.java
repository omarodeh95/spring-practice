package dev.omar_learning.postgres;

import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.domain.Book;

public class TestDataUtil {

  private TestDataUtil(){
  }

  public static Author buildTestAuthor() {
    return Author.builder()
      .id(1L)
      .name("Omar Odeh")
      .age(90)
      .build();
  }

  // This is not clean and should be configurable using the the builTestAuthor parameters
  public static Author buildTestAuthorA() {
    return Author.builder()
      .id(1L)
      .name("Omar Odeh")
      .age(24)
      .build();
  }

  public static Author buildTestAuthorB() {
    return Author.builder()
      .id(2L)
      .name("Omar Odeh")
      .age(40)
      .build();
  }

  public static Author buildTestAuthorC() {
    return Author.builder()
      .id(3L)
      .name("Omar Odeh")
      .age(90)
      .build();
  }

  public static Book buildTestBook(Author author) {
    return Book.builder()
      .isbn("123-456-789")
      .title("Lord of The Ring")
      .author(author)
      .build();
  }

  // This is not clean and should be configurable using the the builTestAuthor parameters
  public static Book buildTestBookA(Author author) {
    return Book.builder()
      .isbn("123-456-78910")
      .title("Lord of The Ring")
      .author(author)
      .build();
  }

  public static Book buildTestBookB(Author author) {
    return Book.builder()
      .isbn("123-456-78911")
      .title("Lord of The Ring")
      .author(author)
      .build();
  }

  public static Book buildTestBookC(Author author) {
    return Book.builder()
      .isbn("123-456-78912")
      .title("Lord of The Ring")
      .author(author)
      .build();
  }
}

