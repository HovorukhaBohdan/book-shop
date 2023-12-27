package com.example.bookshop.repository;

import com.example.bookshop.dto.cart.ShoppingCartResponseDto;
import com.example.bookshop.model.ShoppingCart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUserEmail(String email);
}
