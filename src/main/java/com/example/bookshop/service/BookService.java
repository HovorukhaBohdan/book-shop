package com.example.bookshop.service;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.dto.UpdateBookRequestDto;
import java.util.List;
import java.util.Map;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> getAll();

    BookDto getById(Long id);

    BookDto updateById(Long id, UpdateBookRequestDto bookRequestDto);

    void deleteById(Long id);

    List<BookDto> searchBooks(Map<String, String> params);
}
