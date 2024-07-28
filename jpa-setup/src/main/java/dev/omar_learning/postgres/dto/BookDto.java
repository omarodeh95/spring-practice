package dev.omar_learning.postgres.dto;

import org.springframework.data.annotation.Id;

import dev.omar_learning.postgres.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
  private String isbn;

  private String title;

  private AuthorDto author;
}

