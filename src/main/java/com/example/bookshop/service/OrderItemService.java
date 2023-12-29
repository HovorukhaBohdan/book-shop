package com.example.bookshop.service;

import com.example.bookshop.dto.orderitem.OrderItemResponseDto;
import java.util.List;

public interface OrderItemService {
    List<OrderItemResponseDto> getAllOrderItemsForSpecificOrder(Long userId, Long orderId);

    OrderItemResponseDto getSpecificItemFromOrder(Long userId, Long orderId, Long itemId);
}
