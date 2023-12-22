package com.example.bookshop.dto.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class UpdateBookRequestDto {
    @NotNull(message = "can't be null")
    private String title;
    @NotNull(message = "can't be null")
    private String author;
    @NotNull(message = "can't be null")
    private String isbn;
    @Positive(message = "must be more than 0")
    @NotNull(message = "can't be null")
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<Long> categoryIds;
}
