package dev.omar_learning.postgres.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.omar_learning.postgres.TestDataUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import dev.omar_learning.postgres.domain.Book;
import dev.omar_learning.postgres.domain.Author;
import java.util.Optional;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
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

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {
    // Create authors
    Author authorA = TestDataUtil.buildTestAuthorA();
    authorDao.create(authorA);
    Author authorB = TestDataUtil.buildTestAuthorB();
    authorDao.create(authorB);
    Author authorC = TestDataUtil.buildTestAuthorC();
    authorDao.create(authorC);

    // Create books
    Book bookA = TestDataUtil.buildTestBookA(); // author A
    underTest.create(bookA);
    Book bookB = TestDataUtil.buildTestBookB(); // author B
    underTest.create(bookB);
    Book bookC = TestDataUtil.buildTestBookC(); // author C
    underTest.create(bookC);

    List<Book> books = underTest.all();

    assertThat(books).hasSize(3).containsExactly(bookA, bookB, bookC);

  }

  @Test
  public void testThatBookCanBeUpdatedAndRecalled() {
    // Create authors
    Author authorA = TestDataUtil.buildTestAuthorA();
    authorDao.create(authorA);
    Author authorB = TestDataUtil.buildTestAuthorB();
    authorDao.create(authorB);

    // Create book
    Book bookA = TestDataUtil.buildTestBookA();
    underTest.create(bookA);

    String bookAIsbn = bookA.getIsbn();
    Book fetchedBook = underTest.findOne(bookA.getIsbn()).get();

    fetchedBook.setIsbn("my-test-isbn");
    fetchedBook.setTitle("Dev to mastery");
    fetchedBook.setAuthorId(authorB.getId());

    underTest.update(bookAIsbn, fetchedBook);
    Optional<Book> updatedBook = underTest.findOne(fetchedBook.getIsbn());

    assertThat(updatedBook).isPresent();
    assertThat(updatedBook.get()).isEqualTo(fetchedBook);

  }

  @Test
  public void testThatBookCanBeDeleted() {
    Author authorA = TestDataUtil.buildTestAuthorA();
    authorDao.create(authorA);

    Book bookA = TestDataUtil.buildTestBookA();
    underTest.create(bookA);

    String bookAIsbn = bookA.getIsbn();
    Optional<Book> fetchedBook = underTest.findOne(bookA.getIsbn());
    assertThat(fetchedBook).isPresent();

    underTest.delete(fetchedBook.get().getIsbn());
    Optional<Book> deletedBook = underTest.findOne(bookA.getIsbn());
    assertThat(deletedBook).isNotPresent();

  }
}
