package com.example.bookshop.controller;

import com.example.bookshop.dto.shopping_cart.ShoppingCartResponseDto;
import com.example.bookshop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public List<ShoppingCartResponseDto> getAll(Pageable pageable) {
        return shoppingCartService.getAll(pageable);
    }
}
