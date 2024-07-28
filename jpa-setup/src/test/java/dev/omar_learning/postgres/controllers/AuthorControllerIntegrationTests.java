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
import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.services.AuthorService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private AuthorService authorService;

  @Autowired
  public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
    this.mockMvc = mockMvc;
    this.objectMapper = new ObjectMapper();
    this.authorService = authorService;
  }

  @Test
  public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
    Author testAuthor = TestDataUtil.buildTestAuthor();
    testAuthor.setId(null);

    String authorJson = objectMapper.writeValueAsString(testAuthor);

    mockMvc.perform(
        MockMvcRequestBuilders.post("/authors")
        .contentType(MediaType.APPLICATION_JSON)
        .content(authorJson)
        ).andExpect(
          MockMvcResultMatchers.status().isCreated()
          );

  }

  @Test
  public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
    Author testAuthor = TestDataUtil.buildTestAuthor();
    testAuthor.setId(null);

    String authorJson = objectMapper.writeValueAsString(testAuthor);

    mockMvc.perform(
        MockMvcRequestBuilders.post("/authors")
        .contentType(MediaType.APPLICATION_JSON)
        .content(authorJson)
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.name").value("Omar Odeh")
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.age").value(90)
          );

  }

  
  @Test
  public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/authors")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.status().isOk()
          );
  }

  @Test
  public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
    Author author = TestDataUtil.buildTestAuthorA();
    authorService.save(author);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/authors")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$[0].name").value("Omar Odeh")
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$[0].age").value(24)
          );
  }

  @Test
  public void testThatFindOneAuthorReturnsHttpStatus200() throws Exception {
    Author author = TestDataUtil.buildTestAuthorA();
    authorService.save(author);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/authors/" + author.getId())
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.status().isOk()
          );
  }

  @Test
  public void testThatFindOneAuthorReturnsAuthor() throws Exception {
    Author author = TestDataUtil.buildTestAuthorA();
    Author savedAuthor = authorService.save(author);
    mockMvc.perform(
        MockMvcRequestBuilders.get("/authors/" + savedAuthor.getId())
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.name").value("Omar Odeh")
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.age").value(24)
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
          );
  }

  @Test
  public void testThatNotFoundAuthorReturnsHttpStatus404() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/authors/1")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.status().isNotFound()
          );
  }

  @Test
  public void testThatFullUpdateAuthorReturnsHttpStatus404WhenNotFound() throws Exception {
    Author testAuthor = TestDataUtil.buildTestAuthor();
    testAuthor.setName("Testing");
    testAuthor.setAge(100);

    String authorJsonData = objectMapper.writeValueAsString(testAuthor);


    mockMvc.perform(
        MockMvcRequestBuilders.put("/authors/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(authorJsonData)
        ).andExpect(
          MockMvcResultMatchers.status().isNotFound()
          );
  }

  @Test
  public void testThatFullUpdateAuthorResourceIsReturnedInResponse() throws Exception {
    Author testAuthor = TestDataUtil.buildTestAuthor();
    authorService.save(testAuthor);

    Author newAuthor = TestDataUtil.buildTestAuthor();
    newAuthor.setName("Testing");
    newAuthor.setAge(100);

    String authorJsonData = objectMapper.writeValueAsString(newAuthor);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/authors/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(authorJsonData)
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.id").value(testAuthor.getId())
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.name").value("Testing")
        ).andExpect(
          MockMvcResultMatchers.jsonPath("$.age").value(100)
          );
  }

  @Test
  public void testThatFullUpdateAuthorReturnsHttpStatus200() throws Exception {
    Author testAuthor = TestDataUtil.buildTestAuthor();
    authorService.save(testAuthor);

    Author newAuthor = TestDataUtil.buildTestAuthor();
    newAuthor.setName("Testing");
    newAuthor.setAge(100);

    String authorJsonData = objectMapper.writeValueAsString(newAuthor);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/authors/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(authorJsonData)
        ).andExpect(
          MockMvcResultMatchers.status().isOk()
          );
  }
}
