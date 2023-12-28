package com.example.bookshop.service.impl;

import com.example.bookshop.dto.order.OrderResponseDto;
import com.example.bookshop.mapper.OrderMapper;
import com.example.bookshop.model.Order;
import com.example.bookshop.repository.OrderRepository;
import com.example.bookshop.service.OrderService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponseDto> getOrdersHistory(Long userId, Pageable pageable) {
        List<Order> ordersHistory = orderRepository.findAllByUserId(userId, pageable);
        return ordersHistory.stream()
                .map(orderMapper::toDto)
                .toList();
    }
}
