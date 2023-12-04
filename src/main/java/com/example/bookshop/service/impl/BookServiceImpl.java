package com.example.bookshop.service.impl;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toBookEntity(bookRequestDto);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.getAll().stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.getById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get book with id: " + id)
        );
        return bookMapper.toBookDto(book);
    }
}
