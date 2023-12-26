package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.shopping_cart.ShoppingCartResponseDto;
import com.example.bookshop.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {

    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}
