package com.example.product_junit_testing.model;

import com.example.product_junit_testing.dto.ProductDto;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table (name = "product")
public class Product {

    @Id
    @NotNull()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    public Product(Long id, String name, Double price, Integer quantity, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product(Long id, String name, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product() {
    }

    public Product(ProductDto productDto) {
        this.name = productDto.getName();
        this.quantity = productDto.getQuantity();
        this.price = productDto.getPrice();
    }

    public Product(Long id, ProductDto productDto) {
        this.id = id;
        this.name = productDto.getName();
        this.quantity = productDto.getQuantity();
        this.price = productDto.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
