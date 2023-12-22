package com.example.bookshop.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    @Column(unique = true)
    private String isbn;
    @NonNull
    private BigDecimal price;
    private String description;
    @Column(name = "cover_image")
    private String coverImage;
    @NonNull
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @ManyToMany
    @JoinTable(name = "books_categories",
                joinColumns = @JoinColumn(name = "book_id"),
                inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Book() {

    }
}
