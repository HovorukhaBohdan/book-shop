package com.example.bookshop.service;

import com.example.bookshop.dto.shopping_cart.ShoppingCartResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingCartService {
    List<ShoppingCartResponseDto> getAll(Pageable pageable);
}
