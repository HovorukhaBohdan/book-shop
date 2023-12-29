package com.example.bookshop.service.impl;

import com.example.bookshop.dto.orderitem.OrderItemResponseDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.OrderItemMapper;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.repository.OrderRepository;
import com.example.bookshop.service.OrderItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponseDto> getAllOrderItemsForSpecificOrder(
            Long userId, Long orderId
    ) {
        Order order = getOrderByUserIdAndOrderId(userId, orderId);

        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getSpecificItemFromOrder(Long userId, Long orderId, Long itemId) {
        Order order = getOrderByUserIdAndOrderId(userId, orderId);

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't get order item with id: " + itemId)
                );

        return orderItemMapper.toDto(orderItem);
    }

    private Order getOrderByUserIdAndOrderId(Long userId, Long orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(
                () -> new EntityNotFoundException("Can't get order with id: " + orderId)
        );
    }
}
