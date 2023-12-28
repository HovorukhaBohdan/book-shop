package com.example.bookshop.service;

import com.example.bookshop.dto.order.OrderResponseDto;
import com.example.bookshop.dto.orderitem.OrderItemResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> getOrdersHistory(Long userId, Pageable pageable);
}
