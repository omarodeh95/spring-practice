package dev.omar_learning.postgres.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import dev.omar_learning.postgres.domain.Book;
import dev.omar_learning.postgres.dto.BookDto;
import dev.omar_learning.postgres.mappers.Mapper;

@Component
public class BookMapperImpl implements Mapper<Book, BookDto> {

  private ModelMapper modelMapper;

  public BookMapperImpl(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }


  @Override
  public BookDto mapTo(Book book) {
    return modelMapper.map(book, BookDto.class);
  }

  @Override
  public Book mapFrom(BookDto bookDto) {
    return modelMapper.map(bookDto, Book.class);
  }
}

