package com.example.bookshop.dto.cart;

import com.example.bookshop.dto.item.CartItemResponseDto;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;
}
