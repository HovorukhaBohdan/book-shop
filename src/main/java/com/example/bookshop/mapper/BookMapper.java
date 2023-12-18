package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.dto.book.UpdateBookRequestDto;
import com.example.bookshop.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    Book toBookEntity(CreateBookRequestDto bookRequestDto);

    Book toBookEntity(UpdateBookRequestDto bookRequestDto);
}
