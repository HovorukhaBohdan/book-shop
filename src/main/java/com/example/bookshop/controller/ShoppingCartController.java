package com.example.bookshop.controller;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.dto.item.UpdateRequestCartItemDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.CartItemService;
import com.example.bookshop.service.ShoppingCartService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get a shopping cart with items",
            description = "Get the shopping cart for authenticated user")
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCart(user.getEmail());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Add an item to shopping cart",
            description = "Adding an item to shopping cart")
    public ShoppingCartResponseDto addItem(
            Authentication authentication,
            @RequestBody CartItemRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addItem(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/cart-items/{id}")
    @Operation(summary = "Update an item in shopping cart",
            description = "Update an item in shopping cart by item's id")
    public ShoppingCartResponseDto updateItem(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody UpdateRequestCartItemDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateItem(user.getId(), id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{id}")
    @Operation(summary = "Delete an item from shopping cart",
            description = "Delete an item from shopping cart")
    public void deleteItem(@PathVariable Long id) {
        cartItemService.deleteItem(id);
    }
}
