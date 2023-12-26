package com.example.bookshop.service.impl;

import com.example.bookshop.dto.item.CartItemResponseDto;
import com.example.bookshop.mapper.CartItemMapper;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemResponseDto saveItem(CartItem item) {
        return cartItemMapper.toDto(cartItemRepository.save(item));
    }
}
