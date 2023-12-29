package com.example.bookshop.dto.item;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateRequestCartItemDto {
    @Min(value = 1, message = "quantity value must be more than 0")
    private int quantity;
}
