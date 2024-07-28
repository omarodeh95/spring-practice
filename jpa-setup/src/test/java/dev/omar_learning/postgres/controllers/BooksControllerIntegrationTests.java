package dev.omar_learning.postgres.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.omar_learning.postgres.TestDataUtil;
import dev.omar_learning.postgres.dto.BookDto;
import dev.omar_learning.postgres.services.BookService;
import dev.omar_learning.postgres.domain.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BooksControllerIntegrationTests {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private BookService bookService;

  @Autowired
  public BooksControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
    this.mockMvc = mockMvc;
    this.objectMapper = new ObjectMapper();
    this.bookService = bookService;
  }


  @Test
  public void testThatCreateBookReturnsTheCorrectIsbn() throws Exception {
    BookDto bookDto = TestDataUtil.buildTestBookDto(null);
    String createBookJson = objectMapper.writeValueAsString(bookDto);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
        .contentType(MediaType.APPLICATION_JSON)
        .content(createBookJson)
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
          );
  }

  @Test
  public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
    BookDto bookDto = TestDataUtil.buildTestBookDto(null);
    String createBookJson = objectMapper.writeValueAsString(bookDto);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
        .contentType(MediaType.APPLICATION_JSON)
        .content(createBookJson)
        ).andExpect(
          MockMvcResultMatchers.status().isCreated()
          );
  }

  @Test
  public void testThatListBooksReturnsHttpStatus200() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/books")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.status().isOk()
          );
  }

  @Test
  public void testThatListBooksReturnsListOfBooks() throws Exception {
    Book book = TestDataUtil.buildTestBookA(null);
    bookService.createBook("123-123-123", book);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/books")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$[0].isbn").isString()
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$[0].isbn").value("123-123-123")
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$[0].title").value("Lord of The Rings")
          );
  }
}
