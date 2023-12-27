package com.example.bookshop.service.impl;

import com.example.bookshop.dto.item.CartItemResponseDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CartItemMapper;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public CartItemResponseDto updateItem(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get cart item with id: " + id)
        );

        cartItem.setQuantity(quantity);

        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }
}
