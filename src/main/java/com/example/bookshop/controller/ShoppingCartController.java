package com.example.bookshop.controller;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.ShoppingCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

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
}
