package dev.omar_learning.postgres.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.omar_learning.postgres.TestDataUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import dev.omar_learning.postgres.domain.Book;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationTests {

  private BookDaoImpl underTest;

  @Autowired
  public BookDaoImplIntegrationTests(BookDaoImpl underTest) {
    this.underTest = underTest;
  }

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    Book book = TestDataUtil.buildTestBook();

    underTest.create(book);

    Optional<Book> result = underTest.findOne(book.getIsbn());

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }
}
