package com.example.bookshop.mapper;

import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CategoryRequestDto;
import com.example.bookshop.model.Category;

public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toCategoryEntity(CategoryRequestDto requestDto);
}
