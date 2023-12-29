package com.example.bookshop.dto.orderitem;

import com.example.bookshop.model.Order;
import lombok.Data;

@Data
public class UpdateRequestOrderItemDto {
    private Order.Status status;
}
