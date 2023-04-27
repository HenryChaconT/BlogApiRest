package com.Blog.service;


import com.Blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long idCategory);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(CategoryDto categoryDto,Long id);

    void deleteCategory(Long id);
}
