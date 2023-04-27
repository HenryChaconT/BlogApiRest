package com.udemy.controller;

import com.udemy.entity.Category;
import com.udemy.payload.CategoryDto;
import com.udemy.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createdCategory(@RequestBody CategoryDto categoryDto){

        CategoryDto savedCategory= categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<CategoryDto> getCategory(@PathVariable(name = "id") Long idCategory){

        CategoryDto getCategory=categoryService.getCategory(idCategory);

        return ResponseEntity.ok(getCategory);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){

        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable(name = "id") Long id){

        CategoryDto categoryDtoSaved=categoryService.updateCategory(categoryDto,id);

        return new ResponseEntity<>(categoryDtoSaved,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long id){

        categoryService.deleteCategory(id);

        return new ResponseEntity<>("Category delete successfully",HttpStatus.OK);
    }
}
