package com.example.bookshop.controller;

import com.example.bookshop.dto.order.OrderResponseDto;
import com.example.bookshop.dto.orderitem.OrderItemRequestDto;
import com.example.bookshop.dto.orderitem.OrderItemResponseDto;
import com.example.bookshop.dto.orderitem.UpdateRequestOrderItemDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.OrderItemService;
import com.example.bookshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get orders history", description = "Get a list of all orders")
    public List<OrderResponseDto> getOrdersHistory(
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrdersHistory(user.getId(), pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Place an order", description = "Place an order")
    public OrderResponseDto placeOrder(
            Authentication authentication,
            Pageable pageable,
            @RequestBody @Valid OrderItemRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user, pageable, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update an order status",
            description = "Updates status for specific order")
    public OrderResponseDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestOrderItemDto requestDto
    ) {
        return orderService.updateOrderStatus(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get all item from a certain order",
            description = "Get all item from a certain order")
    public List<OrderItemResponseDto> getAllOrderItemsForSpecificOrder(
            Authentication authentication,
            @PathVariable Long orderId
    ) {
        User user = (User) authentication.getPrincipal();
        return orderItemService.getAllOrderItemsForSpecificOrder(user.getId(), orderId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get a specific item from a certain order",
            description = "Get a specific item from a certain order")
    public OrderItemResponseDto getSpecificItemFromOrder(
            Authentication authentication,
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        User user = (User) authentication.getPrincipal();
        return orderItemService.getSpecificItemFromOrder(user.getId(), orderId, itemId);
    }
}
