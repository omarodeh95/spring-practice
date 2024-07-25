package dev.omar_learning.postgres.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.omar_learning.postgres.TestDataUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import dev.omar_learning.postgres.domain.Book;
import dev.omar_learning.postgres.domain.Author;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationTests {

  private BookDaoImpl underTest;
  private AuthorDaoImpl authorDao;

  @Autowired
  public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDaoImpl authorDao) {
    this.underTest = underTest;
    this.authorDao = authorDao;
  }

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    // Create author
    Author author = TestDataUtil.buildTestAuthor();
    authorDao.create(author);

    // Create Book
    Book book = TestDataUtil.buildTestBook();
    book.setAuthorId(author.getId());
    underTest.create(book);

    Optional<Book> result = underTest.findOne(book.getIsbn());

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }
}
