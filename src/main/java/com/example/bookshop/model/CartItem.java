package com.example.bookshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    @Column(nullable = false)
    private ShoppingCart shoppingCart;
    @OneToOne
    @JoinColumn(name = "book_id")
    @Column(nullable = false)
    private Book book;
    private int quantity;
}
