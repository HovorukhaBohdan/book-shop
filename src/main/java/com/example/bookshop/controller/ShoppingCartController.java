package com.example.bookshop.controller;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.dto.item.CartItemResponseDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.CartItemService;
import com.example.bookshop.service.ShoppingCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @GetMapping
    public List<ShoppingCartResponseDto> getAll(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getAll(user.getEmail(), pageable);
    }

    @PostMapping
    public ShoppingCartResponseDto addItem(
            Authentication authentication,
            @RequestBody CartItemRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addItem(user.getId(), requestDto);
    }

    @PutMapping("/cart-items/{id}")
    public CartItemResponseDto updateItem(@PathVariable Long id, @RequestBody int quantity) {
        return cartItemService.updateItem(id, quantity);
    }
}
