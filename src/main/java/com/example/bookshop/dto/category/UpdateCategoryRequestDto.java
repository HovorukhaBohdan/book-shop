package com.example.bookshop.dto.category;

import lombok.Data;

@Data
public class UpdateCategoryRequestDto {
    private Long id;
    private String name;
    private String description;
}
