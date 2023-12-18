package com.example.bookshop.mapper;

import com.example.bookshop.config.MapperConfig;
import com.example.bookshop.dto.user.UserRegistrationRequestDto;
import com.example.bookshop.dto.user.UserResponseDto;
import com.example.bookshop.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toUserDto(User user);

    User toUserEntity(UserRegistrationRequestDto requestDto);
}
