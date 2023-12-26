package com.example.bookshop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToStringExclude
    @EqualsExclude
    @HashCodeExclude
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart shoppingCart;

    @ToStringExclude
    @EqualsExclude
    @HashCodeExclude
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    private int quantity;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}

