package dev.omar_learning.postgres.repositores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import dev.omar_learning.postgres.TestDataUtil;
import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.repositores.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

  private AuthorRepository underTest;

  @Autowired
  public AuthorRepositoryIntegrationTests (AuthorRepository underTest) {
    this.underTest = underTest;
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled() {

    Author author = TestDataUtil.buildTestAuthor();
    underTest.save(author);
    Optional<Author> result = underTest.findById(author.getId());

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);

  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    Author authorA = TestDataUtil.buildTestAuthorA();
    underTest.save(authorA);
    Author authorB = TestDataUtil.buildTestAuthorB();
    underTest.save(authorB);
    Author authorC = TestDataUtil.buildTestAuthorC();
    underTest.save(authorC);

    Iterable<Author> authors = underTest.findAll();

    assertThat(authors).hasSize(3).containsExactly(authorA, authorB, authorC);

  }

  @Test
  public void testThatAuthorCanBeUpdatedAndRecalled() {
    Author authorA = TestDataUtil.buildTestAuthorA();
    underTest.save(authorA);

    Author fetchedAuthor = underTest.findById(authorA.getId()).get();

    fetchedAuthor.setName("Omar");
    fetchedAuthor.setAge(20);

    underTest.save(fetchedAuthor);

    Optional<Author> updatedAuthor = underTest.findById(fetchedAuthor.getId());
    assertThat(updatedAuthor).isPresent();
    assertThat(updatedAuthor.get()).isEqualTo(fetchedAuthor);

  }

  @Test
  public void testThatAuthorCanBeDeleted() {
    // save author
    Author authorA = TestDataUtil.buildTestAuthorA();
    underTest.save(authorA);

    // fetch author
    Optional<Author> fetchedAuthor = underTest.findById(authorA.getId());
    assertThat(fetchedAuthor).isPresent();

    underTest.delete(fetchedAuthor.get());

    Optional<Author> deletedAuthor = underTest.findById(authorA.getId());
    assertThat(deletedAuthor).isNotPresent();

  }

  @Test
  public void testThatGetAuthorsWithAgeLessThan() {
    Author testAuthorA = TestDataUtil.buildTestAuthorA();
    Author testAuthorB = TestDataUtil.buildTestAuthorB();
    Author testAuthorC = TestDataUtil.buildTestAuthorC();
    underTest.save(testAuthorA);
    underTest.save(testAuthorB);
    underTest.save(testAuthorC);

    underTest.ageLessThan(50);

    Iterable<Author> result = underTest.ageLessThan(50);
    assertThat(result).containsExactly(testAuthorA, testAuthorB);

  }

  @Test
  public void testThatGetAuthorsWithAgeGreaterThan() {
    Author testAuthorA = TestDataUtil.buildTestAuthorA();
    Author testAuthorB = TestDataUtil.buildTestAuthorB();
    Author testAuthorC = TestDataUtil.buildTestAuthorC();
    underTest.save(testAuthorA);
    underTest.save(testAuthorB);
    underTest.save(testAuthorC);

    Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(50);

    assertThat(result).containsExactly(testAuthorC);
  }
}
