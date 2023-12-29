package com.example.bookshop.dto.orderitem;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotEmpty(message = "can't be empty")
    private String shippingAddress;
}
