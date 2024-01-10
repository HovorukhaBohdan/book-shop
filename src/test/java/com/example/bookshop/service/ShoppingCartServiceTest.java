package com.example.bookshop.service;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.dto.item.CartItemResponseDto;
import com.example.bookshop.dto.item.UpdateRequestCartItemDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CartItemMapper;
import com.example.bookshop.mapper.ShoppingCartMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.Category;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.service.impl.ShoppingCartServiceImpl;
import java.math.BigDecimal;
import java.util.HashSet;
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
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {
    private static User user;
    private static Book book;
    private static ShoppingCart shoppingCart;
    private static CartItem cartItem;
    private static CartItemResponseDto updatedCartItemDto;
    private static ShoppingCart shoppingCartWithCartItem;
    private static ShoppingCartResponseDto shoppingCartDto;
    private static ShoppingCartResponseDto shoppingCartDtoWithItem;
    private static ShoppingCartResponseDto updatedShoppingCart;
    private static CartItemRequestDto validCartItemRequestDto;
    private static CartItemRequestDto emptyRequest;
    private static UpdateRequestCartItemDto validUpdateRequest;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private CartItemMapper cartItemMapper;
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeAll
    static void beforeAll() {
        user = new User()
                .setId(3L)
                .setEmail("bob@gmail.com")
                .setPassword("$2a$10$yCASXP59HTCOdYDPCt3W7.dBNYpo/o99j2ywUg6jGhYLoFaRp.k.G")
                .setFirstName("Bob")
                .setLastName("Smith")
                .setShippingAddress("Kyiv city");

        Category category = new Category()
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

        shoppingCart = new ShoppingCart()
                .setId(3L)
                .setUser(user)
                .setCartItems(new HashSet<>());

        cartItem = new CartItem()
                .setId(1L)
                .setShoppingCart(shoppingCart)
                .setBook(book)
                .setQuantity(10);

        updatedCartItemDto = new CartItemResponseDto()
                .setId(1L)
                .setBookId(1L)
                .setBookTitle("It")
                .setQuantity(5);

        shoppingCartWithCartItem = new ShoppingCart()
                .setId(3L)
                .setUser(user)
                .setCartItems(Set.of(cartItem));

        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto()
                .setId(1L)
                .setBookId(1L)
                .setBookTitle("It")
                .setQuantity(10);

        shoppingCartDto = new ShoppingCartResponseDto()
                .setId(3L)
                .setUserId(3L)
                .setCartItems(List.of());

        shoppingCartDtoWithItem = new ShoppingCartResponseDto()
                .setId(3L)
                .setUserId(3L)
                .setCartItems(List.of(cartItemResponseDto));

        updatedShoppingCart = new ShoppingCartResponseDto()
                .setId(3L)
                .setUserId(3L)
                .setCartItems(List.of(updatedCartItemDto));

        validCartItemRequestDto = new CartItemRequestDto()
                .setBookId(1L)
                .setQuantity(10);

        emptyRequest = new CartItemRequestDto();

        validUpdateRequest = new UpdateRequestCartItemDto()
                .setQuantity(5);
    }

    @Test
    @DisplayName("Get shopping cart")
    void getShoppingCart_ValidEmail_ReturnShoppingCartDto() {
        Mockito.when(shoppingCartRepository.findByUserEmail(user.getEmail()))
                .thenReturn(shoppingCart);

        ShoppingCartResponseDto expected = shoppingCartDto;
        ShoppingCartResponseDto actual = shoppingCartService.getShoppingCart(user.getEmail());

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Add item to shopping cart")
    void addItem_ValidUserIdAndValidCartItemRequest_Success() {
        Long userId = 3L;
        Long bookId = 1L;

        Mockito.when(cartItemMapper.toEntity(validCartItemRequestDto)).thenReturn(cartItem);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(shoppingCartRepository.findByUserId(userId))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        ShoppingCartResponseDto expected = shoppingCartDtoWithItem;
        ShoppingCartResponseDto actual = shoppingCartService.addItem(
                userId, validCartItemRequestDto
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Add item to shopping cart with invalid user id")
    void addItem_InvalidUserId_EntityNotFoundExceptionThrown() {
        Long userId = 3L;
        Long bookId = 1L;

        Mockito.when(cartItemMapper.toEntity(validCartItemRequestDto)).thenReturn(cartItem);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get shopping cart with id: " + userId,
                EntityNotFoundException.class,
                () -> shoppingCartService.addItem(userId, validCartItemRequestDto)
        );
    }

    @Test
    @DisplayName("Add item to shopping cart with invalid request")
    void addItem_InvalidRequest_EntityNotFoundExceptionThrown() {
        Long userId = 3L;
        Long bookId = 1L;

        Mockito.when(cartItemMapper.toEntity(validCartItemRequestDto)).thenReturn(cartItem);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get book with id: " + bookId,
                EntityNotFoundException.class,
                () -> shoppingCartService.addItem(userId, validCartItemRequestDto)
        );
    }

    @Test
    @DisplayName("Update item")
    void updateItem_ValidParams_Success() {
        Long userId = 3L;
        Long itemId = 1L;

        Mockito.when(shoppingCartRepository.findByUserId(userId))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(cartItemRepository.findByIdAndShoppingCartId(itemId, userId))
                .thenReturn(Optional.of(cartItem));

        ShoppingCartResponseDto expected = updatedShoppingCart;
        ShoppingCartResponseDto actual = shoppingCartService.updateItem(
                userId, itemId, validUpdateRequest
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Update item with invalid user id")
    void updateItem_InvalidUserId_EntityNotFoundExceptionThrown() {
        Long userId = 3L;
        Long itemId = 1L;

        Mockito.when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get shopping cart with id: " + userId,
                EntityNotFoundException.class,
                () -> shoppingCartService.updateItem(userId, itemId, validUpdateRequest)
        );
    }

    @Test
    void updateItem_InvalidItemId_EntityNotFoundExceptionThrown() {
        Long userId = 3L;
        Long itemId = 1L;

        Mockito.when(shoppingCartRepository.findByUserId(userId))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(cartItemRepository.findByIdAndShoppingCartId(itemId, userId))
                .thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get item with id: " + itemId,
                EntityNotFoundException.class,
                () -> shoppingCartService.updateItem(userId, itemId, validUpdateRequest)
        );
    }
}
