package com.example.product_junit_testing.service;


import com.example.product_junit_testing.dto.ProductDto;
import com.example.product_junit_testing.model.Product;
import com.example.product_junit_testing.repository.ProductRepository;
import com.example.product_junit_testing.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){

        return productRepository.findAll();
    }

    public ResponseEntity<?> getById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return ResponseEntity.ok(new APIResponse(true,"",product.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false,"Product not found"));
    }
    public ResponseEntity<?> create(ProductDto productDto){
        System.out.println(productDto.getName()+" " + productDto.getPrice()+" "+ productDto.getQuantity());
     Product product = new Product(productDto);
      Product savedProduct = productRepository.save(product);
      return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(true, "Product created successfully", savedProduct));
    }
    public ResponseEntity<?> update(Long id,ProductDto productDto ){
        Optional<Product> productFoundById = productRepository.findById(id);
        if(productFoundById.isPresent()){
            Product product = productFoundById.get();
            if(productRepository.existsByName(productDto.getName()) && !(productDto.getName().equalsIgnoreCase(product.getName()))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(false, "Product name already exists"));
            }
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            productRepository.save(product);
            return ResponseEntity.ok(new APIResponse(true,"Product updated successfully", product));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false,"Product Not found"));
    }

    public ResponseEntity<?> delete(Long id){
        Optional<Product> productFoundById  = productRepository.findById(id);
        if(productFoundById.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.ok(new APIResponse(true,"Product Deleted Successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Product not found"));
    }
}
