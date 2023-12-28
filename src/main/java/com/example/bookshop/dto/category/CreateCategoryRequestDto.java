package com.example.bookshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotBlank(message = "Enter name of category")
    private String name;
    private String description;
}
