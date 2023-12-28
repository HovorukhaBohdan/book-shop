package com.example.bookshop.repository;

import com.example.bookshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc JOIN FETCH sc.cartItems")
    ShoppingCart findByUserEmail(String email);

    Optional<ShoppingCart> findByUserId(Long userId);
}
