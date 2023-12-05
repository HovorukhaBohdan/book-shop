package com.example.bookshop.service;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> getAll();

    BookDto getById(Long id);

    void deleteById(Long id);
}
