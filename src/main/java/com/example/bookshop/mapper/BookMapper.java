package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.dto.book.UpdateBookRequestDto;
import com.example.bookshop.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {CategoryMapper.class})
public interface BookMapper {
    @Mapping(target = "categoryIds")
    BookDto toBookDto(Book book);

    @Mapping(source = "categoryIds", target = "categories")
    Book toBookEntity(CreateBookRequestDto bookRequestDto);

    @Mapping(source = "categoryIds", target = "categories")
    Book toBookEntity(UpdateBookRequestDto bookRequestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
