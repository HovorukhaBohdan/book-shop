package com.example.bookshop.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.dto.category.UpdateCategoryRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
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
class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private static CategoryDto horror;
    private static CategoryDto detective;
    private static CategoryDto mystic;
    private static CategoryDto drama;
    private static CategoryDto updatedDrama;
    private static CategoryDto testCategoryDto;
    private static BookDtoWithoutCategoryIds firestarter;
    private static CreateCategoryRequestDto testCategory;
    private static CreateCategoryRequestDto emptyRequestDto;
    private static UpdateCategoryRequestDto validUpdateRequest;
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

        horror = new CategoryDto()
                .setId(1L)
                .setName("Horror")
                .setDescription("Horror category");

        detective = new CategoryDto()
                .setId(2L)
                .setName("Detective")
                .setDescription("Detective category");

        mystic = new CategoryDto()
                .setId(3L)
                .setName("Mystic")
                .setDescription("Mystic category");

        drama = new CategoryDto()
                .setId(4L)
                .setName("Drama")
                .setDescription("Drama category");

        updatedDrama = new CategoryDto()
                .setId(4L)
                .setName("Deleted")
                .setDescription("Deleted");

        testCategoryDto = new CategoryDto()
                .setId(5L)
                .setName("Romantic")
                .setDescription("Romantic category");

        firestarter = new BookDtoWithoutCategoryIds()
                .setId(2L)
                .setTitle("Firestarter")
                .setAuthor("Stephen King")
                .setIsbn("987-654-321")
                .setPrice(BigDecimal.valueOf(12.35))
                .setDescription("Book named \"Firestarter\"");

        testCategory = new CreateCategoryRequestDto()
                .setName("Romantic")
                .setDescription("Romantic category");

        emptyRequestDto = new CreateCategoryRequestDto();

        validUpdateRequest = new UpdateCategoryRequestDto()
                .setName("Deleted")
                .setDescription("Deleted");
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
    @DisplayName("Get all categories")
    void getAll_ValidRequest_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                    get("/api/categories")
                )
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryDto> expected = List.of(horror, detective, mystic, drama);
        CategoryDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                CategoryDto[].class
        );

        Assert.assertEquals(4, actual.length);
        Assert.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get category by valid id")
    void getCategoryById_ValidCategoryId_Success() throws Exception {
        Long id = 1L;

        MvcResult mvcResult = mockMvc.perform(
                        get("/api/categories/{id}", id)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto expected = horror;
        CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                CategoryDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get category by invalid id")
    void getCategoryById_InvalidCategoryId_BadRequest() throws Exception {
        Long invalidId = 100L;

        mockMvc.perform(
                get("/api/categories/{id}", invalidId)
                )
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create new category with valid request")
    void createCategory_ValidRequest_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                post("/api/categories")
                        .content(objectMapper.writeValueAsString(testCategory))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto expected = testCategoryDto;
        CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                CategoryDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create new category with invalid request")
    void createCategory_InvalidRequest_BadRequest() throws Exception {
        mockMvc.perform(
                post("/api/categories")
                        .content(objectMapper.writeValueAsString(emptyRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update category with valid id and valid request")
    void updateCategory_ValidCategoryIdAndValidRequest_Success() throws Exception {
        Long id = 4L;

        MvcResult mvcResult = mockMvc.perform(
                put("/api/categories/{id}", id)
                        .content(objectMapper.writeValueAsString(validUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto expected = updatedDrama;
        CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                CategoryDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update category with invalid id")
    void updateCategory_InvalidCategoryId_BadRequest() throws Exception {
        Long id = 100L;

        mockMvc.perform(
                put("/api/categories/{id}", id)
                        .contentType(objectMapper.writeValueAsString(validUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update category with valid id and invalid request")
    void updateCategory_ValidCategoryIdAndInvalidRequest_BadRequest() throws Exception {
        Long id = 4L;

        mockMvc.perform(
                put("/api/categories/{id}", id)
                        .content(objectMapper.writeValueAsString(emptyRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete category by valid id")
    void deleteCategoryById_ValidId_Success() throws Exception {
        Long id = 2L;

        mockMvc.perform(
                delete("/api/books/{id}", id)
                )
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get books by valid category id")
    void getBooksByCategoryId_ValidCategoryId_ReturnListWithOneBookDto() throws Exception {
        Long categoryId = 1L;

        MvcResult mvcResult = mockMvc.perform(
                get("/api/categories/{id}/books", categoryId)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<BookDtoWithoutCategoryIds> expected = List.of(firestarter);
        BookDtoWithoutCategoryIds[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                BookDtoWithoutCategoryIds[].class
        );

        Assert.assertEquals(1, actual.length);
        Assert.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get books by valid category id")
    void getBooksByCategoryId_InvalidCategoryId_BadRequest() throws Exception {
        Long invalidId = 100L;

        mockMvc.perform(
                get("/api/categories/{id}/books", invalidId)
                )
                .andExpect(status().isBadRequest());
    }
}
