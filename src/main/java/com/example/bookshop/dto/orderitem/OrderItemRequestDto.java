package com.example.bookshop.dto.orderitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class OrderItemRequestDto {
    @NotEmpty(message = "can't be empty")
    private String shippingAddress;
}
