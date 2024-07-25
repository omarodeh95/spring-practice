package dev.omar_learning.postgres.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import dev.omar_learning.postgres.TestDataUtil;
import dev.omar_learning.postgres.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests {

  private AuthorDaoImpl underTest;

  @Autowired
  public AuthorDaoImplIntegrationTests (AuthorDaoImpl underTest) {
    this.underTest = underTest;
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled() {

    Author author = TestDataUtil.buildTestAuthor();
    underTest.create(author);
    Optional<Author> result = underTest.findOne(author.getId());

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);

  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    Author authorA = TestDataUtil.buildTestAuthorA();
    underTest.create(authorA);
    Author authorB = TestDataUtil.buildTestAuthorB();
    underTest.create(authorB);
    Author authorC = TestDataUtil.buildTestAuthorC();
    underTest.create(authorC);

    List<Author> authors = underTest.all();

    assertThat(authors).hasSize(3).containsExactly(authorA, authorB, authorC);

  }
}

