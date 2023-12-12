package com.example.bookshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateBookRequestDto {
    @NotNull(message = "Field 'title' can't be null")
    private String title;
    @NotNull(message = "Field 'author' can't be null")
    private String author;
    @NotNull(message = "Field 'isbn' can't be null")
    private String isbn;
    @Positive(message = "Field 'price' must be more than 0")
    @NotNull(message = "Field 'price' can't be null")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
