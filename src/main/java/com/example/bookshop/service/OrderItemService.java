package com.example.bookshop.service;

import com.example.bookshop.dto.orderitem.OrderItemResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponseDto> getAllOrderItemsForSpecificOrder(Long userId, Long orderId, Pageable pageable);

    OrderItemResponseDto getSpecificItemFromOrder(Long userId, Long orderId, Long itemId);
}
