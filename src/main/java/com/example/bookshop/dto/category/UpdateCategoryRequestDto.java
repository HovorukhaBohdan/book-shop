package com.example.bookshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCategoryRequestDto {
    @NotBlank(message = "Enter id of category")
    @Positive(message = "Id must be higher than 0")
    private Long id;
    @NotBlank(message = "Enter name of category")
    private String name;
    private String description;
}
