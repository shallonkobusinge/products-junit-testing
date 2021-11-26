package com.example.product_junit_testing.service;


import com.example.product_junit_testing.dto.ProductDto;
import com.example.product_junit_testing.model.Product;
import com.example.product_junit_testing.repository.ProductRepository;
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

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        return null;
    }

    public Product create(ProductDto productDto){
     Product product = new Product(productDto);
     return productRepository.save(product);
    }
    public ResponseEntity<?> update(Long id,ProductDto productDto ){
        Optional<Product> productFoundById = productRepository.findById(id);
        if(productFoundById.isPresent()){
            Product product = productFoundById.get();
            if(productRepository.existsByName(productDto.getName()) && !(productDto.getName().equalsIgnoreCase(product.getName()))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product name already exists");
            }
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            productRepository.save(product);
            return ResponseEntity.ok(product);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    public ResponseEntity<?> delete(Long id){
        Optional<Product> productFoundById  = productRepository.findById(id);
        if(productFoundById.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product Deleted Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }
}