package com.Blog.service.impl;

import com.Blog.entity.Category;
import com.Blog.exception.ResourceNotFoundException;
import com.Blog.payload.CategoryDto;
import com.Blog.repository.CategoryRepository;
import com.Blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category=mapper.map(categoryDto,Category.class);

        Category saved=categoryRepository.save(category);

        return mapper.map(saved,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long idCategory) {

        Category category=categoryRepository.findById(idCategory)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",idCategory));
        return mapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories=categoryRepository.findAll();


        return categories.stream().map(category -> mapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,Long id) {

        Category category=categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));

        category.setDescription(categoryDto.getDescription());
        category.setName(categoryDto.getName());

        Category categorySaved= categoryRepository.save(category);

        return mapper.map(categorySaved,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category=categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));

        categoryRepository.delete(category);

    }
}
