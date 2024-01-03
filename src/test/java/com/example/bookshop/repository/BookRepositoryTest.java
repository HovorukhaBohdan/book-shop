package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find books by category with existing category")
    @Sql(scripts = {
            "classpath:database/books/add-books.sql",
            "classpath:database/categories/add-categories.sql",
            "classpath:database/categories/add-books-categories.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-books-categories.sql",
            "classpath:database/categories/delete-categories.sql",
            "classpath:database/books/delete-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategory_CategoriesWithIdThree_ReturnOneBook() {
        Category mystic = new Category()
                .setId(3L)
                .setName("Mystic")
                .setDescription("Mystic category");

        Book it = new Book()
                .setId(3L)
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(8.55))
                .setDescription("Book named \"It\"");

        List<Book> expected = List.of(it);
        List<Book> actual = bookRepository.getBooksByCategoriesContaining(mystic);

        Assert.assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id");
    }

    @Test
    @DisplayName("Find books by category with not used category")
    @Sql(scripts = {
            "classpath:database/books/add-books.sql",
            "classpath:database/categories/add-categories.sql",
            "classpath:database/categories/add-books-categories.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-books-categories.sql",
            "classpath:database/categories/delete-categories.sql",
            "classpath:database/books/delete-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategory_NotUsedCategory_ReturnEmptyList() {
        Category drama = new Category()
                .setId(4L)
                .setName("Drama")
                .setDescription("Drama category");

        List<Book> actual = bookRepository.getBooksByCategoriesContaining(drama);

        Assert.assertEquals(0, actual.size());
    }
}
