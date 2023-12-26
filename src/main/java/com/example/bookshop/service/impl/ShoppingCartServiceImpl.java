package com.example.bookshop.service.impl;

import com.example.bookshop.dto.shopping_cart.ShoppingCartResponseDto;
import com.example.bookshop.mapper.ShoppingCartMapper;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.service.ShoppingCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public List<ShoppingCartResponseDto> getAll(Pageable pageable) {
        return shoppingCartRepository.findAll(pageable).stream()
                .map(shoppingCartMapper::toDto)
                .toList();
    }
}
