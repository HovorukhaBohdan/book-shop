package com.example.bookshop.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookSearchParametersDto;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.dto.book.UpdateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;
    private static BookDto it;
    private static BookDto firestarter;
    private static BookDto cujo;
    private static BookDto returnedFromPost;
    private static BookDto returnedFromUpdate;
    private static CreateBookRequestDto validCreateRequestDto;
    private static CreateBookRequestDto emptyCreateRequestDto;
    private static UpdateBookRequestDto validUpdateRequestDto;
    private static UpdateBookRequestDto emptyUpdateRequestDto;
    private static BookSearchParametersDto validParams;
    private static BookSearchParametersDto invalidParams;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();

        teardown(dataSource);

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/add-books.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/add-categories.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/add-books-categories.sql")
            );
        }

        it = new BookDto()
                .setId(1L)
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("123-456-789")
                .setPrice(BigDecimal.valueOf(8.55))
                .setDescription("Book named \"It\"")
                .setCategoryIds(Set.of(3L));

        firestarter = new BookDto()
                .setId(2L)
                .setTitle("Firestarter")
                .setAuthor("Stephen King")
                .setIsbn("987-654-321")
                .setPrice(BigDecimal.valueOf(12.35))
                .setDescription("Book named \"Firestarter\"")
                .setCategoryIds(Set.of(1L));

        cujo = new BookDto()
                .setId(3L)
                .setTitle("Cujo")
                .setAuthor("Stephen King")
                .setIsbn("123-789-456")
                .setPrice(BigDecimal.valueOf(15.95))
                .setDescription("Book named \"Cujo\"")
                .setCategoryIds(Set.of(2L));

        validCreateRequestDto = new CreateBookRequestDto()
                .setTitle("Tests")
                .setAuthor("Test author")
                .setIsbn("111-111-111")
                .setPrice(BigDecimal.valueOf(12.37))
                .setDescription("Tests description")
                .setCoverImage("Tests cover image")
                .setCategoryIds(Set.of(1L));

        emptyCreateRequestDto = new CreateBookRequestDto();

        returnedFromPost = new BookDto()
                .setId(4L)
                .setTitle("Tests")
                .setAuthor("Test author")
                .setIsbn("111-111-111")
                .setPrice(BigDecimal.valueOf(12.37))
                .setDescription("Tests description")
                .setCoverImage("Tests cover image")
                .setCategoryIds(Set.of(1L));

        returnedFromUpdate = new BookDto()
                .setId(1L)
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("111-222-333")
                .setPrice(BigDecimal.valueOf(10.10))
                .setDescription("Description was deleted")
                .setCoverImage("Cover image")
                .setCategoryIds(Set.of(3L));

        validUpdateRequestDto = new UpdateBookRequestDto()
                .setTitle("It")
                .setAuthor("Stephen King")
                .setIsbn("111-222-333")
                .setPrice(BigDecimal.valueOf(10.10))
                .setDescription("Description was deleted")
                .setCoverImage("Cover image")
                .setCategoryIds(Set.of(3L));

        emptyUpdateRequestDto = new UpdateBookRequestDto();

        validParams = new BookSearchParametersDto(
                new String[]{"Firestarter"},
                new String[]{},
                new String[]{}
        );

        invalidParams = new BookSearchParametersDto(
                new String[]{"Book"},
                new String[]{},
                new String[]{}
        );
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/delete-books-categories.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/delete-categories.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/delete-books.sql")
            );
        }
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all books")
    void getAllBooks_ValidRequest_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/api/books")
                )
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> expected = List.of(it, firestarter, cujo);
        BookDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                BookDto[].class
        );

        Assert.assertEquals(3, actual.length);
        Assert.assertEquals(expected, Arrays.stream(actual).collect(Collectors.toList()));
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get book by valid id")
    void getBookById_ValidId_Success() throws Exception {
        Long id = 1L;
        MvcResult mvcResult = mockMvc.perform(
                get("/api/books/{id}", id)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto expected = it;
        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                BookDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get book by invalid id")
    void getBookById_InvalidId_BadRequest() throws Exception {
        Long invalidId = 100L;
        
        mockMvc.perform(
                get("/api/books/{id}", invalidId)
                )
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create book with valid request")
    void createBook_ValidRequest_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                post("/api/books")
                        .content(objectMapper.writeValueAsString(validCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto expected = returnedFromPost;
        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                BookDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create book with invalid request")
    void createBook_InvalidRequest_BadRequest() throws Exception {
        mockMvc.perform(
                post("/api/books")
                    .content(objectMapper.writeValueAsString(emptyCreateRequestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update book by valid id with valid request")
    void updateBookById_ValidBookIdAndValidRequestDto_Success() throws Exception {
        Long id = 1L;

        MvcResult mvcResult = mockMvc.perform(
                put("/api/books/{id}", id)
                        .content(objectMapper.writeValueAsString(validUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto expected = returnedFromUpdate;
        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                BookDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update book by invalid id")
    void updateBookById_InvalidBookIdAndValidRequestDto_BadRequest() throws Exception {
        Long invalidId = 100L;

        mockMvc.perform(
                put("/api/books/{id}", invalidId)
                        .content(objectMapper.writeValueAsString(validUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update book by valid id and invalid request")
    void updateBookById_ValidBookIdAndInvalidRequestDto_BadRequest() throws Exception {
        Long id = 1L;

        mockMvc.perform(
                put("/api/books/{id}", id)
                        .content(objectMapper.writeValueAsString(emptyUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete book by valid id")
    void deleteBookById_ValidBookId_NoContent() throws Exception {
        Long id = 3L;

        mockMvc.perform(
                delete("/api/books/{id}", id)
                )
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Search books by valid params")
    void searchBooks_ValidBookSearchParams_ReturnListWitOneBookDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/api/books/search")
                        .param("title", validParams.title())
                )
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> expected = List.of(firestarter);
        BookDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                BookDto[].class
        );

        Assert.assertEquals(1, actual.length);
        Assert.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Search books by valid params")
    void searchBooks_InvalidBookSearchParams_ReturnEmptyList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/books/search")
                                .param("title", invalidParams.title())
                        )
                        .andExpect(status().isOk())
                        .andReturn();

        List<BookDto> expected = List.of();
        BookDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                BookDto[].class
        );

        Assert.assertEquals(0, actual.length);
        Assert.assertEquals(expected, Arrays.stream(actual).toList());
    }
}
