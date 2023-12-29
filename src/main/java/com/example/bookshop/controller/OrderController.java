package com.example.bookshop.controller;

import com.example.bookshop.dto.order.OrderResponseDto;
import com.example.bookshop.dto.orderitem.OrderItemRequestDto;
import com.example.bookshop.dto.orderitem.OrderItemResponseDto;
import com.example.bookshop.dto.orderitem.UpdateRequestOrderItemDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponseDto> getOrdersHistory(
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrdersHistory(user.getId(), pageable);
    }

    @PostMapping
    public OrderResponseDto placeOrder(
            Authentication authentication,
            Pageable pageable,
            @RequestBody OrderItemRequestDto requestDto
            ) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user, pageable, requestDto);
    }

    @PatchMapping("/{id}")
    public OrderResponseDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateRequestOrderItemDto requestDto
    ) {
     return orderService.updateOrderStatus(id, requestDto);
    }
}
