package com.example.bookshop.service.impl;

import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public void deleteItem(Long id) {
        cartItemRepository.delete(getItemById(id));
    }

    private CartItem getItemById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get cart item with id: " + id)
        );
    }
}
