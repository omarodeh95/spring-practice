package dev.omar_learning.postgres.repositories;

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
import dev.omar_learning.postgres.repositores.AuthorRepository;
import dev.omar_learning.postgres.repositores.BookRepository;
import dev.omar_learning.postgres.domain.Author;
import java.util.Optional;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

  private BookRepository underTest;

  @Autowired
  public BookRepositoryIntegrationTests(BookRepository underTest) {
    this.underTest = underTest;
  }

  @Test
  public void testThatBookCanBesavedAndRecalled() {
    // save author
    Author author = TestDataUtil.buildTestAuthor();

    // save Book
    Book book = TestDataUtil.buildTestBook(author);
    underTest.save(book);

    Optional<Book> result = underTest.findById(book.getIsbn());

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

  @Test
  public void testThatMultipleBooksCanBesavedAndRecalled() {
    // save authors
    Author authorA = TestDataUtil.buildTestAuthorA();
    Author authorB = TestDataUtil.buildTestAuthorB();
    Author authorC = TestDataUtil.buildTestAuthorC();

    // save books
    Book bookA = TestDataUtil.buildTestBookA(authorA); // author A
    underTest.save(bookA);
    Book bookB = TestDataUtil.buildTestBookB(authorB); // author B
    underTest.save(bookB);
    Book bookC = TestDataUtil.buildTestBookC(authorC); // author C
    underTest.save(bookC);

    Iterable<Book> books = underTest.findAll();

    assertThat(books).hasSize(3).containsExactly(bookA, bookB, bookC);

  }

  @Test
  public void testThatBookCanBeUpdatedAndRecalled() {
    // save authors
    Author authorA = TestDataUtil.buildTestAuthorA();
    Author authorB = TestDataUtil.buildTestAuthorB();

    // save book
    Book bookA = TestDataUtil.buildTestBookA(authorA);
    underTest.save(bookA);

    String bookAIsbn = bookA.getIsbn();
    Book fetchedBook = underTest.findById(bookA.getIsbn()).get();

    fetchedBook.setIsbn("my-test-isbn");
    fetchedBook.setTitle("Zero to mastery");

    underTest.save(fetchedBook);
    Optional<Book> updatedBook = underTest.findById(fetchedBook.getIsbn());

    assertThat(updatedBook).isPresent();
    assertThat(updatedBook.get()).isEqualTo(fetchedBook);

  }

  @Test
  public void testThatBookCanBeDeleted() {
    Author authorA = TestDataUtil.buildTestAuthorA();

    Book bookA = TestDataUtil.buildTestBookA(authorA);
    underTest.save(bookA);

    String bookAIsbn = bookA.getIsbn();
    Optional<Book> fetchedBook = underTest.findById(bookA.getIsbn());
    assertThat(fetchedBook).isPresent();

    underTest.delete(fetchedBook.get());
    Optional<Book> deletedBook = underTest.findById(bookA.getIsbn());
    assertThat(deletedBook).isNotPresent();

  }
}
