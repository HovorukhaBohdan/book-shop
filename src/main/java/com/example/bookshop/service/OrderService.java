package com.example.bookshop.service;

import com.example.bookshop.dto.order.OrderResponseDto;
import com.example.bookshop.dto.orderitem.OrderItemRequestDto;
import com.example.bookshop.dto.orderitem.UpdateRequestOrderItemDto;
import com.example.bookshop.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    List<OrderResponseDto> getOrdersHistory(Long userId, Pageable pageable);

    OrderResponseDto placeOrder(User user, OrderItemRequestDto requestDto);

    OrderResponseDto updateOrderStatus(Long id, UpdateRequestOrderItemDto requestDto);
}
