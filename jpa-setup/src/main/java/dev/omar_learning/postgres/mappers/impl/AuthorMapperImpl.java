package dev.omar_learning.postgres.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import dev.omar_learning.postgres.domain.Author;
import dev.omar_learning.postgres.dto.AuthorDto;
import dev.omar_learning.postgres.mappers.Mapper;

@Component
public class AuthorMapperImpl implements Mapper<Author, AuthorDto> {

  private ModelMapper modelMapper;

  public AuthorMapperImpl(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }


  @Override
  public AuthorDto mapTo(Author author) {
    return modelMapper.map(author, AuthorDto.class);
  }

  @Override
  public Author mapFrom(AuthorDto authorDto) {
    return modelMapper.map(authorDto, Author.class);
  }
}
