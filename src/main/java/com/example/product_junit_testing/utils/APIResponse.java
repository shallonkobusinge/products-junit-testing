package com.example.product_junit_testing.utils;

import com.example.product_junit_testing.model.Product;

public class APIResponse {
    private boolean status;
    private String message;
    private Product data;

    public APIResponse(boolean status, String message, Product data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public APIResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
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

    public void setData(Product data) {
        this.data = data;
    }

}
