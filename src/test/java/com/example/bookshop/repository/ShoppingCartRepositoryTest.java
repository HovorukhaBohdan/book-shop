package com.example.bookshop.repository;

import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    private static User user;
    private static ShoppingCart shoppingCart;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeAll
    static void beforeAll() {
        user = new User()
                .setId(3L)
                .setEmail("bob@gmail.com")
                .setPassword("$2a$10$yCASXP59HTCOdYDPCt3W7.dBNYpo/o99j2ywUg6jGhYLoFaRp.k.G")
                .setFirstName("Bob")
                .setLastName("Smith")
                .setShippingAddress("Kyiv city");

        shoppingCart = new ShoppingCart()
                .setId(3L)
                .setUser(user);
    }

    @Test
    @DisplayName("Find shopping cart by user email")
    @Sql(scripts = {
            "classpath:database/users/insert-users.sql",
            "classpath:database/carts/insert-shopping-cart.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/carts/delete-shopping-carts.sql",
            "classpath:database/users/delete-users.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByUserEmail_ValidEmail_ReturnShoppingCart() {
        ShoppingCart expected = shoppingCart;
        ShoppingCart actual = shoppingCartRepository.findByUserEmail(user.getEmail());

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
