package com.example.bookshop.service;

import com.example.bookshop.dto.category.CategoryDto;
import com.example.bookshop.dto.category.CreateCategoryRequestDto;
import com.example.bookshop.dto.category.UpdateCategoryRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CategoryMapper;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.impl.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    private static Category category;
    private static Category updatedCategory;
    private static CategoryDto categoryDto;
    private static CategoryDto updatedCategoryDto;
    private static CreateCategoryRequestDto createCategoryRequestDto;
    private static UpdateCategoryRequestDto updateCategoryRequestDto;

    @BeforeAll
    static void beforeAll() {
        category = new Category()
                .setId(1L)
                .setName("Horror")
                .setDescription("Horror category");

        updatedCategory = new Category()
                .setId(1L)
                .setName("Mystic")
                .setDescription("Mystic category");

        categoryDto =  new CategoryDto()
                .setId(1L)
                .setName("Horror")
                .setDescription("Horror category");

        updatedCategoryDto = new CategoryDto()
                .setId(1L)
                .setName("Horror")
                .setDescription("Horror category");

        createCategoryRequestDto = new CreateCategoryRequestDto()
                .setName("Horror")
                .setDescription("Horror category");

        updateCategoryRequestDto = new UpdateCategoryRequestDto()
                .setName("Mystic")
                .setDescription("Mystic category");

    }

    @Test
    @DisplayName("Find all categories with one saved category")
    void findAll_OneSavedCategory_ReturnListWithOneCategory() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Mockito.when(categoryRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(category)));

        List<Category> expected = List.of(category);
        List<CategoryDto> actual = categoryService.findAll(pageRequest);

        Assert.assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id");
    }

    @Test
    @DisplayName("Get category by valid id")
    void getById_ValidCategoryId_ReturnCategoryDto() {
        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryDto expected = categoryDto;
        CategoryDto actual = categoryService.getById(id);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Get category by invalid id")
    void getById_InvalidCategoryId_ReturnCategoryDto() {
        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get category with id: " + id,
                EntityNotFoundException.class,
                () -> categoryService.getById(id)
        );
    }

    @Test
    @DisplayName("Save category with valid request")
    void save_ValidCategory_ReturnCategoryDto() {
        Mockito.when(categoryMapper.toCategoryEntity(createCategoryRequestDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto expected = categoryDto;
        CategoryDto actual = categoryService.save(createCategoryRequestDto);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Update category by valid id")
    void update_ValidCategoryId_ReturnCategoryDto() {
        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toCategoryEntity(updateCategoryRequestDto)).thenReturn(updatedCategory);

        CategoryDto expected = updatedCategoryDto;
        CategoryDto actual = categoryService.update(id, updateCategoryRequestDto);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Update category by invalid id")
    void update_inValidCategoryId_EntityNotFoundExceptionThrown() {
        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Assert.assertThrows(
                "Can't get category with id: " + id,
                EntityNotFoundException.class,
                () -> categoryService.update(id, updateCategoryRequestDto)
        );
    }
}