package dev.omar_learning.postgres.dao.impl;

import dev.omar_learning.postgres.dao.impl.AuthorDaoImpl;
import dev.omar_learning.postgres.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import org.springframework.jdbc.core.RowMapper;
import dev.omar_learning.postgres.TestDataUtil;


@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {


  @Mock
  private JdbcTemplate jdbcTemplate; // This is injected in the underTest object

  @InjectMocks
  private AuthorDaoImpl underTest;  // Specify where to inject mocks

  @Test
  public void testThatCreateAuthorGeneratesCorrectSql() {
    Author author = TestDataUtil.buildTestAuthor();

    underTest.create(author);

    verify(jdbcTemplate).update(
        eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
        eq(1L),
        eq("Omar Odeh"),
        eq(90)
        );
  }

  @Test
  public void testThatFindOneAuthorGenerattesCorrectSql() {
    // Create Author to read from
    underTest.findOne(1L);
    
    verify(jdbcTemplate).query(
        eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
        ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
        eq(1L)
        );

  }

  @Test
  public void testThatAllAuthorGeneratesCorrectSql() {
    underTest.all();

    verify(jdbcTemplate).query(
        eq("SELECT id, name, age FROM authors"),
        ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
  }

  @Test
  public void testThatAllUpdateGeneratesCorrectSql() {
    Author author = TestDataUtil.buildTestAuthor();
    Long authorId = author.getId();

    author.setId(2L);
    author.setName("Omar");
    author.setAge(20);
    underTest.update(authorId, author);

    verify(jdbcTemplate).update(
        eq("UPDATE authors SET id = ?, name = ?, age = ? WHERE authors.id = ?"),
        eq(author.getId()),
        eq(author.getName()),
        eq(author.getAge()),
        eq(authorId)
        );
  }

  @Test
  public void testThatDeleteAuthorGeneratesCorrectSql() {
    Author author = TestDataUtil.buildTestAuthor();

    underTest.delete(author.getId());

    verify(jdbcTemplate).update(
        eq("DELETE FROM authors WHERE id = ?"),
        eq(author.getId())
        );
  }

}
