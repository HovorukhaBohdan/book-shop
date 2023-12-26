package com.example.bookshop.model;

import jakarta.persistence.*;

import java.util.Set;
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
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ToStringExclude
    @EqualsExclude
    @HashCodeExclude
    @OneToMany
    @JoinTable(name = "shopping_cart_items",
               joinColumns = @JoinColumn(name = "shopping_cart_id"),
               inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<CartItem> cartItems;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
