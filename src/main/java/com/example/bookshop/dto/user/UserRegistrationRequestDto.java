package com.example.bookshop.dto.user;

import com.example.bookshop.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@FieldMatch
public class UserRegistrationRequestDto {
    @Email(message = "wrong format of email")
    @NotNull(message = "can't be null")
    private String email;
    @NotNull(message = "can't be null")
    private String password;
    @NotNull(message = "can't be null")
    private String repeatPassword;
    @NotNull(message = "can't be null")
    private String firstName;
    @NotNull(message = "can't be null")
    private String lastName;
    private String shippingAddress;
}
