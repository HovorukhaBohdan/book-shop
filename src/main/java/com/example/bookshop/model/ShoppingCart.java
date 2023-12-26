package com.example.bookshop.model;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany
    @JoinTable(name = "shopping_cart_items",
               joinColumns = @JoinColumn(name = "shopping_cart_id"),
               inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<CartItem> cartItems;
}
