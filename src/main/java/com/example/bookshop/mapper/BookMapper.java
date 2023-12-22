package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.dto.book.UpdateBookRequestDto;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class, uses = {CategoryMapper.class})
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toBookDto(Book book);

    Book toBookEntity(CreateBookRequestDto bookRequestDto);

    Book toBookEntity(UpdateBookRequestDto bookRequestDto);

    BookDtoWithoutCategoryIds toBookDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }
}
