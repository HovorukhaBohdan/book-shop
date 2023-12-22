package com.example.bookshop.service.impl;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.BookSearchParametersDto;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.dto.book.UpdateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.repository.specification.SpecificationBuilder;
import com.example.bookshop.service.BookService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final SpecificationBuilder<Book> bookSpecificationBuilder;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toBookEntity(bookRequestDto);

        Set<Category> categories = bookRequestDto.getCategoryIds().stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Can't get category with id: " + id)
                ))
                .collect(Collectors.toSet());

        book.setCategories(categories);

        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
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

        Set<Category> categories = bookRequestDto.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId).orElseThrow(
                    () -> new EntityNotFoundException("Can't get category with id: " + categoryId)
                ))
                .collect(Collectors.toSet());

        bookFromRequest.setCategories(categories);

        return bookMapper.toBookDto(bookRepository.save(bookFromRequest));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        Specification<Book> specification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id) {
        Category categoryById = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get books with category id: " + id)
        );

        return bookRepository.getBooksByCategoriesContaining(categoryById).stream()
                .map(bookMapper::toBookDtoWithoutCategories)
                .toList();
    }
}
