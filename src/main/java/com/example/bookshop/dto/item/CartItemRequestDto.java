package com.example.bookshop.dto.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItemRequestDto {
    @Min(value = 1, message = "bookId value must be more than 0")
    @NotNull(message = "bookId mustn't be null")
    private Long bookId;
    @Min(value = 1, message = "quantity value must be more than 0")
    private int quantity;
}
