package com.example.bookshop.service;

import com.example.bookshop.dto.book.*;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.repository.book.BookSpecificationBuilder;
import com.example.bookshop.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;
    @InjectMocks
    private BookServiceImpl bookService;
    private static Book book;
    private static Book updatedBook;
    private static Category category;
    private static BookDto bookDto;
    private static BookDto updatedBookDto;
    private static BookDtoWithoutCategoryIds bookDtoWithoutCategoryIds;
    private static CreateBookRequestDto createBookRequestDto;
    private static UpdateBookRequestDto updateBookRequestDto;
    private static BookSearchParametersDto searchParametersDto;

    @BeforeAll
    static void beforeAll() {
        category = new Category()
                .setId(1L)
                .setName("Horror")
                .setDescription("Horror category");

        book = new Book()
                .setId(1L)
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(12.55))
                .setDescription("Interesting description")
                .setCoverImage("No cover image")
                .setCategories(Set.of(category));

        bookDto = new BookDto()
                .setId(1L)
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(12.55))
                .setDescription("Interesting description")
                .setCoverImage("No cover image")
                .setCategoryIds(Set.of(1L));

        bookDtoWithoutCategoryIds = new BookDtoWithoutCategoryIds()
                .setId(1L)
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(12.55))
                .setDescription("Interesting description")
                .setCoverImage("No cover image");


        updatedBook = new Book()
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(14.88))
                .setDescription("Description was deleted")
                .setCoverImage("Cover image was added")
                .setCategories(Set.of(category));

        updatedBookDto = new BookDto()
                .setId(1L)
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(14.88))
                .setDescription("Description was deleted")
                .setCoverImage("Cover image was added")
                .setCategoryIds(Set.of(1L));

        createBookRequestDto = new CreateBookRequestDto()
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(12.55))
                .setDescription("Interesting description")
                .setCoverImage("No cover image")
                .setCategoryIds(Set.of(1L));

        updateBookRequestDto = new UpdateBookRequestDto()
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(14.88))
                .setDescription("Description was deleted")
                .setCoverImage("Cover image was added")
                .setCategoryIds(Set.of(1L));

        searchParametersDto = new BookSearchParametersDto(
                new String[]{"It"},
                new String[]{"Stephen King"},
                new String[]{"123-456-789"}
        );
    }

    @Test
    @DisplayName("Save book to DB and get DTO for it")
    void saveBook_AllFields_GetBookDtoWithAllFields() {
        Mockito.when(bookMapper.toBookEntity(createBookRequestDto)).thenReturn(book);
        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        BookDto expected = bookDto;
        BookDto actual = bookService.save(createBookRequestDto);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Get all books from DB")
    void getAll_OneSavedBook_GetListWithOneBookDto() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(bookRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(book)));

        List<BookDto> expected = List.of(bookDto);
        List<BookDto> actual = bookService.getAll(pageRequest);

        Assert.assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id");
    }

    @Test
    @DisplayName("Get book by valid id from DB")
    void getById_ValidId_ReturnBookDto() {
        Long id = 1L;

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookDto expected = bookDto;
        BookDto actual = bookService.getById(id);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Get book by invalid id from DB")
    void getById_InvalidId_EntityNotFoundExceptionThrown() {
        Long id = 2L;

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get book with id: " + id,
                EntityNotFoundException.class,
                () -> bookService.getById(id)
        );
    }

    @Test
    @DisplayName("Update book with valid id")
    void updateById_ValidBookId_ReturnBookDto() {
        Long id = 1L;

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toBookEntity(updateBookRequestDto)).thenReturn(updatedBook);
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        BookDto expected = updatedBookDto;
        BookDto actual = bookService.updateById(id, updateBookRequestDto);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Update book with invalid id")
    void updateById_InvalidBookId_EntityNotFoundExceptionThrown() {
        Long id = 1L;

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get book with id: " + id,
                EntityNotFoundException.class,
                () -> bookService.updateById(id, updateBookRequestDto)
        );
    }

    @Test
    @DisplayName("Update book with invalid category id")
    void updateById_InvalidCategoryId_EntityNotFoundExceptionThrown() {
        Long id = 1L;

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toBookEntity(updateBookRequestDto)).thenReturn(updatedBook);
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get category with id: " + id,
                EntityNotFoundException.class,
                () -> bookService.updateById(id, updateBookRequestDto)
        );
    }

    @Test
    @DisplayName("Search book by valid params")
    void searchBooks_ValidParams_ReturnListWithOneBookDto() {
        Mockito.when(bookSpecificationBuilder.build(searchParametersDto))
                .thenReturn(Specification.where(null));
        Mockito.when(bookRepository.findAll(Specification.where(null)))
                .thenReturn(List.of(book));

        List<BookDto> expected = List.of(bookDto);
        List<BookDto> actual = bookService.searchBooks(searchParametersDto);

        Assert.assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id");
    }

    @Test
    @DisplayName("Search book by invalid params")
    void searchBooks_InvalidParams_ReturnEmptyList() {
        Mockito.when(bookSpecificationBuilder.build(searchParametersDto))
                .thenReturn(Specification.where(null));
        Mockito.when(bookRepository.findAll(Specification.where(null)))
                .thenReturn(List.of());

        List<BookDto> actual = bookService.searchBooks(searchParametersDto);

        Assert.assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Search book by valid category id")
    void getBooksByCategoryId_ValidCategoryId_ReturnListWitOneBookDto() {
        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Mockito.when(bookRepository.getBooksByCategoriesContaining(category)).thenReturn(List.of(book));

        List<BookDtoWithoutCategoryIds> expected = List.of(bookDtoWithoutCategoryIds);
        List<BookDtoWithoutCategoryIds> actual = bookService.getBooksByCategoryId(id);

        Assert.assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id");
    }

    @Test
    @DisplayName("Search book by invalid category id")
    void getBooksByCategoryId_InvalidCategoryId_ReturnEmptyList() {
        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get books with category id: " + id,
                EntityNotFoundException.class,
                () -> bookService.getBooksByCategoryId(id)
        );
    }
}