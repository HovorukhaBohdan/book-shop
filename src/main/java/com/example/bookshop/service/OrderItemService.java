package com.example.bookshop.service;

import com.example.bookshop.dto.orderitem.OrderItemResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    List<OrderItemResponseDto> getAllOrderItemsForSpecificOrder(Pageable pageable, Long orderId);

    OrderItemResponseDto getSpecificItemFromOrder(Long userId, Long orderId, Long itemId);
}
