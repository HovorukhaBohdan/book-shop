package com.example.bookshop.service;

import com.example.bookshop.dto.item.CartItemResponseDto;
import com.example.bookshop.model.CartItem;

public interface CartItemService {
    CartItemResponseDto updateItem(Long id, int quantity);

    void deleteItem(Long id);
}
