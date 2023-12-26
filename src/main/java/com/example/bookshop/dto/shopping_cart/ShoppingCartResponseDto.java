package com.example.bookshop.dto.shopping_cart;

import com.example.bookshop.dto.cart_item.CartItemResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;
}
