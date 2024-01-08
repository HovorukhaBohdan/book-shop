package com.example.bookshop.controller;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.dto.item.CartItemResponseDto;
import com.example.bookshop.dto.item.UpdateRequestCartItemDto;
import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;
    private static CartItemRequestDto addItemRequest;
    private static UpdateRequestCartItemDto updateItemRequest;
    private static CartItemResponseDto first;
    private static CartItemResponseDto updatedFirstItem;
    private static CartItemResponseDto second;
    private static CartItemResponseDto third;
    private static ShoppingCartResponseDto responseShoppingCart;
    private static ShoppingCartResponseDto responseShoppingCartWithAddedItem;
    private static ShoppingCartResponseDto responseShoppingCartWithUpdatedItem;
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
                    new ClassPathResource("database/users/insert-users.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/carts/insert-shopping-cart.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/items/insert-cart-items.sql")
            );
        }

        addItemRequest = new CartItemRequestDto()
                .setBookId(3L)
                .setQuantity(30);

        updateItemRequest = new UpdateRequestCartItemDto()
                .setQuantity(1);

        first = new CartItemResponseDto()
                .setId(1L)
                .setBookId(1L)
                .setBookTitle("It")
                .setQuantity(10);

        updatedFirstItem = new CartItemResponseDto()
                .setId(1L)
                .setBookId(1L)
                .setBookTitle("It")
                .setQuantity(1);

        second = new CartItemResponseDto()
                .setId(2L)
                .setBookId(2L)
                .setBookTitle("Firestarter")
                .setQuantity(20);

        third = new CartItemResponseDto()
                .setId(3L)
                .setBookId(3L)
                .setBookTitle("Cujo")
                .setQuantity(30);

        responseShoppingCart = new ShoppingCartResponseDto()
                .setId(3L)
                .setUserId(3L)
                .setCartItems(List.of(first, second));

        responseShoppingCartWithAddedItem = new ShoppingCartResponseDto()
                .setId(3L)
                .setUserId(3L)
                .setCartItems(List.of(first, second, third));

        responseShoppingCartWithUpdatedItem = new ShoppingCartResponseDto()
                .setId(3L)
                .setUserId(3L)
                .setCartItems(List.of(updatedFirstItem, second, third));
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
                    new ClassPathResource("database/items/delete-cart-items.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/carts/delete-shopping-carts.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/users/delete-users.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/delete-books.sql")
            );
        }
    }

    @BeforeEach
    void setUp() {
        Role role = new Role()
                .setId(1L)
                .setName(Role.RoleName.USER);

        User user = new User()
                .setId(3L)
                .setEmail("bob@gmail.com")
                .setPassword("$2a$10$yCASXP59HTCOdYDPCt3W7.dBNYpo/o99j2ywUg6jGhYLoFaRp.k.G")
                .setRoles(Set.of(role));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get a shopping cart with items")
    void getShoppingCart_ValidUser_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/api/cart")
                )
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartResponseDto expected = responseShoppingCart;
        ShoppingCartResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                ShoppingCartResponseDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Add item to shopping cart")
    void addItem_ValidRequest_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/cart")
                                .content(objectMapper.writeValueAsString(addItemRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartResponseDto expected = responseShoppingCartWithAddedItem;
        ShoppingCartResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                ShoppingCartResponseDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Update an item in the shopping cart")
    void updateItem_ValidRequest_Success() throws Exception {
        Long itemId = 1L;

        MvcResult mvcResult = mockMvc.perform(
                put("/api/cart/cart-items/{id}", itemId)
                        .content(objectMapper.writeValueAsString(updateItemRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartResponseDto expected = responseShoppingCartWithUpdatedItem;
        ShoppingCartResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                ShoppingCartResponseDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Delete an item from shopping cart")
    void deleteItem_ValidId_Success() throws Exception {
        Long itemId = 1L;

        mockMvc.perform(
                delete("/api/cart/cart-items/{id}", itemId)
                )
                .andExpect(status().isNoContent());
    }
}
