package dev.omar_learning.postgres.dao;

import dev.omar_learning.postgres.dao.impl.AuthorDaoImpl;
import dev.omar_learning.postgres.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;


@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {


  @Mock
  private JdbcTemplate jdbcTemplate; // This is injected in the underTest object

  @InjectMocks
  private AuthorDaoImpl underTest;  // Specify where to inject mocks

  @Test
  public void testThatCreateAuthorGeneratesCorrectSql() {
    Author author = Author.builder()
      .id(1L)
      .name("Omar Odeh")
      .age(90)
      .build();

    underTest.create(author);

    verify(jdbcTemplate).update(
        eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
        eq(1L),
        eq("Omar Odeh"),
        eq(90)
        );
  }
}
