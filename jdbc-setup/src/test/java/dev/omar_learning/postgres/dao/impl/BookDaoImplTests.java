package dev.omar_learning.postgres.dao.impl;

import dev.omar_learning.postgres.dao.impl.BookDaoImpl;
import dev.omar_learning.postgres.domain.Book;
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
public class BookDaoImplTests {


  @Mock
  private JdbcTemplate jdbcTemplate; // This is injected in the underTest object

  @InjectMocks
  private BookDaoImpl underTest;  // Specify where to inject mocks

  @Test
  public void testThatCreateBookGeneratesCorrectSql() {

    // I need to create the author for the foreign key
    Book book = TestDataUtil.buildTestBook();

    underTest.create(book);

    verify(jdbcTemplate).update(
        eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
        eq("123-456-789"),
        eq("Lord of The Ring"),
        eq(1L)
        );
  }

  @Test
  public void testThatFindOneBookGeneratesCorrectSql() {
    underTest.findOne("123-456-789");

    verify(jdbcTemplate).query(
        eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
        ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
        eq("123-456-789")
        );
  }

  @Test
  public void testThatAllBookGeneratesCorrectSql() {
    underTest.all();

    verify(jdbcTemplate).query(
        eq("SELECT isbn, title, author_id FROM books"),
        ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
  }

   @Test
   public void testThatUpdateBookGeneratesCorrectSql() {
     Book book = TestDataUtil.buildTestBook();
     String updatedBookIsbn = book.getIsbn();

     // Update book attributes
     book.setIsbn("543-543-123");
     book.setTitle("Zero to mastery");
     book.setAuthorId(23L);


     underTest.update(updatedBookIsbn, book);

     verify(jdbcTemplate).update(
         eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
         eq(book.getIsbn()),
         eq(book.getTitle()),
         eq(book.getAuthorId()),
         eq(updatedBookIsbn)
         );
   }

   @Test
   public void testThatBookDeleteGeneratesCorrectSql() {
     Book book = TestDataUtil.buildTestBook();

     underTest.delete(book.getIsbn());
   }
}
