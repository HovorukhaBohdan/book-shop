package com.example.bookshop.repository;

import com.example.bookshop.model.ShoppingCart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserEmail(String email);
}
