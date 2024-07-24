package dev.omar_learning.postgres.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

  private String isbn;

  private String title;

  private Long authorId;
}
