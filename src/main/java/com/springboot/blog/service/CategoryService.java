package com.springboot.blog.service;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(Long id , CategoryDto categoryDto);

    String deleteCategory(Long id);
}
