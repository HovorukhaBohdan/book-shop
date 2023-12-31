package com.example.bookshop.service.impl;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.dto.item.UpdateRequestCartItemDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CartItemMapper;
import com.example.bookshop.mapper.ShoppingCartMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCart(String email) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserEmail(email));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addItem(Long id, CartItemRequestDto requestDto) {
        CartItem cartItem = cartItemMapper.toEntity(requestDto);
        Book bookById = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Can't get book with id: " + id)
        );

        cartItem.setBook(bookById);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get shopping cart with id: " + id)
        );
        cartItem.setShoppingCart(shoppingCart);

        CartItem savedItem = cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(savedItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateItem(
            Long userId,
            Long itemId,
            UpdateRequestCartItemDto requestDto
    ) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                        () -> new EntityNotFoundException("Can't get shopping cart with id: "
                                + userId)
                );

        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(itemId, userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't get item with id: " + itemId)
                );

        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(cartItem.getShoppingCart());
    }
}
