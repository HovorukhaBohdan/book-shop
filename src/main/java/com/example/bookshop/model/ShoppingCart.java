package com.example.bookshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;

@Entity
@Data
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany
    @JoinTable(name = "shopping_cart_items",
               joinColumns = @JoinColumn(name = "shopping_cart_id"),
               inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<CartItem> cartItems;
}
