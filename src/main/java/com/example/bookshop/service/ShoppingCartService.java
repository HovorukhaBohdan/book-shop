package com.example.bookshop.service;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {
    void createShoppingCart(User user);

    List<ShoppingCartResponseDto> getAll(String email, Pageable pageable);

    ShoppingCartResponseDto addItem(Long id, CartItemRequestDto requestDto);
}

