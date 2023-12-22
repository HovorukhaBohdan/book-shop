package com.example.bookshop.service;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookSearchParametersDto;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.dto.book.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> getAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto updateById(Long id, UpdateBookRequestDto bookRequestDto);

    void deleteById(Long id);

    List<BookDto> searchBooks(BookSearchParametersDto searchParameters);

    List<BookDto> getBooksByCategoryId(Long id);
}
