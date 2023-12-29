package com.example.bookshop.service.impl;

import com.example.bookshop.dto.order.OrderResponseDto;
import com.example.bookshop.dto.orderitem.OrderItemRequestDto;
import com.example.bookshop.dto.orderitem.UpdateRequestOrderItemDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.OrderItemMapper;
import com.example.bookshop.mapper.OrderMapper;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.OrderItem;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.repository.OrderItemRepository;
import com.example.bookshop.repository.OrderRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderResponseDto> getOrdersHistory(Long userId, Pageable pageable) {
        List<Order> ordersHistory = orderRepository.findAllByUserId(userId, pageable);
        return ordersHistory.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto placeOrder(
            User user,
            Pageable pageable,
            OrderItemRequestDto requestDto
    ) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Can't get shopping cart with users id: "
                        + user.getId())
        );

        Order order = formOrderWithoutItems(shoppingCart, user, requestDto.getShippingAddress());
        Order savedOrder = orderRepository.save(order);

        Set<OrderItem> orderItems = new HashSet<>(orderItemRepository.saveAll(
                formOrderItems(shoppingCart, order)
        ));
        order.setOrderItems(orderItems);

        cartItemRepository.deleteAll(shoppingCart.getCartItems());

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, UpdateRequestOrderItemDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get order with id: " + id)
        );
        order.setStatus(requestDto.getStatus());

        return orderMapper.toDto(orderRepository.save(order));
    }

    private Order formOrderWithoutItems(ShoppingCart cart, User user, String address) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(i -> i.getBook().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setTotal(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(address);

        return order;
    }

    private Set<OrderItem> formOrderItems(ShoppingCart shoppingCart, Order order) {
        return shoppingCart.getCartItems().stream()
                .map(i -> new OrderItem(
                        order,
                        i.getBook(),
                        i.getQuantity(),
                        i.getBook().getPrice()
                ))
                .collect(Collectors.toSet());
    }
}
