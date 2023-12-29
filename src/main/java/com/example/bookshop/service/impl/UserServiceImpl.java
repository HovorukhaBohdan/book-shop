package com.example.bookshop.service.impl;

import com.example.bookshop.dto.user.UserRegistrationRequestDto;
import com.example.bookshop.dto.user.UserResponseDto;
import com.example.bookshop.exception.RegistrationException;
import com.example.bookshop.mapper.UserMapper;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.ShoppingCartService;
import com.example.bookshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        User user = userMapper.toUserEntity(requestDto);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RegistrationException(String.format(
                    "User with email: %s is already exists", user.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        shoppingCartService.createShoppingCart(user);

        return userMapper.toUserDto(userRepository.save(user));
    }
}
