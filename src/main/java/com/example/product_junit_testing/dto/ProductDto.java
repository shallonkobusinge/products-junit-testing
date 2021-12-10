package com.example.product_junit_testing.dto;

import com.example.product_junit_testing.model.Category;
import com.sun.istack.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ProductDto {
    @NotNull
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private Integer quantity;

    private Category category;

    public ProductDto(String name, Double price, Integer quantity, Category category) {
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

    public ProductDto() {
    }

    public ProductDto( @NotNull String name, @NotNull Double price, @NotNull Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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
