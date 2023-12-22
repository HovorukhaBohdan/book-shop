package com.example.bookshop.mapper;

import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.dto.category.UpdateCategoryRequestDto;
import com.example.bookshop.model.Category;

public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toCategoryEntity(CreateCategoryRequestDto requestDto);

    Category toCategoryEntity(UpdateCategoryRequestDto requestDto);
}
