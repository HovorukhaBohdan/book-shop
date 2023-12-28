package com.example.bookshop.controller;

import com.example.bookshop.dto.order.OrderResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    @GetMapping
    public List<OrderResponseDto> getOrdersHistory() {
        return null;
    }
}
