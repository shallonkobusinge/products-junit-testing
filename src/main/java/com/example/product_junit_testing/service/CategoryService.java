package com.example.product_junit_testing.service;

import com.example.product_junit_testing.dto.CategoryDto;
import com.example.product_junit_testing.dto.ProductDto;
import com.example.product_junit_testing.model.Category;
import com.example.product_junit_testing.model.Product;
import com.example.product_junit_testing.repository.CategoryRepository;
import com.example.product_junit_testing.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }
    public ResponseEntity<APIResponse> getById(Integer id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return ResponseEntity.ok(new APIResponse(true,"",category.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false,"Category not found"));
    }

    public ResponseEntity<APIResponse> create(CategoryDto categoryDto){

        Category category = new Category(categoryDto);
        if(categoryRepository.existsByName(category.getName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(false, "Category name already exists"));
        }
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(true,"Category created successfully", savedCategory));
          }


    public ResponseEntity<APIResponse> update(Integer id,CategoryDto categoryDto ){
        Optional<Category> categoryFoundById = categoryRepository.findById(id);
        if (categoryFoundById.isPresent()){
            Category category = categoryFoundById.get();
        if(categoryRepository.existsByName(categoryDto.getName()) && !(categoryDto.getName().equalsIgnoreCase(categoryDto.getName()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(false,"Category name already exists"));
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return ResponseEntity.ok(new APIResponse(true,"Category updated successfully", category));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false,"Category Not found"));
    }
    public ResponseEntity<APIResponse> delete(Integer id){
        Optional<Category> categoryFoundById  = categoryRepository.findById(id);
        if(categoryFoundById.isPresent()){
            categoryRepository.deleteById(id);
            return ResponseEntity.ok(new APIResponse(true,"Category Deleted Successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Category not found"));
    }
}
