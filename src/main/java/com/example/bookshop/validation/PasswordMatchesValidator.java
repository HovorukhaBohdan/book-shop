package com.example.bookshop.validation;

import com.example.bookshop.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<FieldMatch, UserRegistrationRequestDto> {

    @Override
    public boolean isValid(
            UserRegistrationRequestDto requestDto,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return requestDto.getPassword().equals(requestDto.getRepeatPassword());
    }
}
