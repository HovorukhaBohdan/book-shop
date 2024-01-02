package com.example.bookshop.dto.orderitem;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotBlank(message = "can't be empty")
    private String shippingAddress;
}
