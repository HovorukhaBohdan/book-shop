package com.example.bookshop.controller;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.dto.item.CartItemRequestDto;
import com.example.bookshop.dto.item.CartItemResponseDto;
import com.example.bookshop.dto.item.UpdateRequestCartItemDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.CartItemService;
import com.example.bookshop.service.ShoppingCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @GetMapping
    public List<ShoppingCartResponseDto> getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getAll(user.getEmail());
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
    public ShoppingCartResponseDto updateItem(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody UpdateRequestCartItemDto requestDto
            ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateItem(user.getId(), id, requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{id}")
    public void deleteItem(@PathVariable Long id) {
        cartItemService.deleteItem(id);
    }
}
