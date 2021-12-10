package com.example.product_junit_testing.utils;

import com.example.product_junit_testing.model.Category;
import com.example.product_junit_testing.model.Product;

public class APIResponse {
    private boolean status;
    private String message;
    private Product data;
    private Category category;

    public APIResponse(boolean status, String message, Product data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public APIResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public APIResponse(boolean status, String message, Category category) {
        this.status = status;
        this.message = message;
        this.category = category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getData() {
        return data;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setData(Product data) {
        this.data = data;
    }

}
