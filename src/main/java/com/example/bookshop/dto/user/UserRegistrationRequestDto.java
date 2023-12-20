package com.example.bookshop.dto.user;

import com.example.bookshop.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch
public class UserRegistrationRequestDto {
    @Email(message = "wrong format of email")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Length(min = 8, max = 20, message = "Password must be from 8 to 20 characters")
    private String password;
    @NotBlank(message = "Password is required")
    private String repeatPassword;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    private String shippingAddress;
}
