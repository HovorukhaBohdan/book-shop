package com.example.bookshop.service;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.dto.item.UpdateRequestCartItemDto;
import com.example.bookshop.model.User;
import org.springframework.stereotype.Service;

public interface ShoppingCartService {
    void createShoppingCart(User user);

    ShoppingCartResponseDto getShoppingCart(String email);

    ShoppingCartResponseDto addItem(Long id, CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateItem(Long userId, Long itemId,
                                       UpdateRequestCartItemDto requestDto);
}

