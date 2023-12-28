package com.example.bookshop.service.impl;

import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.dto.category.UpdateCategoryRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CategoryMapper;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get category with id: " + id)
        );

        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toCategoryEntity(requestDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, UpdateCategoryRequestDto requestDto) {
        categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't get category with id: " + id)
        );

        Category categoryFromRequest = categoryMapper.toCategoryEntity(requestDto);
        categoryFromRequest.setId(id);
        return categoryMapper.toCategoryDto(categoryRepository.save(categoryFromRequest));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
