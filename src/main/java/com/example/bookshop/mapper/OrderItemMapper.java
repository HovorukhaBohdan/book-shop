package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.orderitem.OrderItemRequestDto;
import com.example.bookshop.dto.orderitem.OrderItemResponseDto;
import com.example.bookshop.dto.orderitem.UpdateRequestOrderItemDto;
import com.example.bookshop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemRequestDto requestDto);

    OrderItem toEntity(UpdateRequestOrderItemDto requestDto);

    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
