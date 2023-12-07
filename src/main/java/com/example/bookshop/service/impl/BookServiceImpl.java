package com.example.bookshop.service.impl;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.dto.UpdateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.SpecificationBuilder;
import com.example.bookshop.service.BookService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final SpecificationBuilder<Book> bookSpecificationBuilder;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toBookEntity(bookRequestDto);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get book with id: " + id)
        );
        return bookMapper.toBookDto(book);
    }

    @Transactional
    @Override
    public BookDto updateById(Long id, UpdateBookRequestDto bookRequestDto) {
        bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't update book with id: " + id));

        Book bookFromRequest = bookMapper.toBookEntity(bookRequestDto);
        bookFromRequest.setId(id);
        return bookMapper.toBookDto(bookRepository.save(bookFromRequest));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> searchBooks(Map<String, String[]> params) {
        Specification<Book> specification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toBookDto)
                .toList();
    }
}
