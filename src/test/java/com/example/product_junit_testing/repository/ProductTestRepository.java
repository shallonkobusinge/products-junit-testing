package com.example.product_junit_testing.repository;

import com.example.product_junit_testing.dto.ProductDto;
import com.example.product_junit_testing.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductTestRepository {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getAll__success(){
        List<Product> products = productRepository.findAll();
        assertEquals(products.size(),3);
    }

    @Test
    public void findOne__success(){
        Optional<Product> product = productRepository.findById(1L);
        assertTrue(product.isPresent());
    }
    @Test
    public void findOne__failure(){
        Optional<Product> product = productRepository.findById(40L);
        assertFalse(product.isPresent());
    }

    @Test
    public void create__success(){
        ProductDto productDto = new ProductDto("Mouse",10000.0,10);
        Product product = productRepository.save(new Product(productDto));
        assertNotNull(product.getId());
        assertEquals(productDto.getName(), product.getName());
        assertEquals(productDto.getPrice(), product.getPrice());
        assertEquals(productDto.getQuantity(), product.getQuantity());
    }

    @Test
    public void update__success(){
        Optional<Product> foundProductById = productRepository.findById(1L);
        Product product = foundProductById.get();
        product.setName("Laptops");
        product.setQuantity(90);
        product.setPrice(9000.0);
        Product savedProduct = productRepository.save(product);
        assertEquals(savedProduct.getName(), product.getName());
        assertEquals(savedProduct.getPrice(), product.getPrice());
        assertEquals(savedProduct.getQuantity(), product.getQuantity());

    }

    @Test(expected = NoSuchElementException.class)
    public void update__failure(){
        Optional<Product> foundProductById = productRepository.findById(10L);
        Product product = foundProductById.get();
        product.setName("Laptops");
        product.setQuantity(90);
        product.setPrice(9000.0);
        Product savedProduct = productRepository.save(product);
        assertFalse(foundProductById.isPresent());
        assertNotEquals(savedProduct.getPrice(), product.getPrice());
        assertNotEquals(savedProduct.getQuantity(), product.getQuantity());

    }

    @Test
    public void delete__success(){
    List<Product> products = productRepository.findAll();
    productRepository.deleteById(1L);
    List<Product> newProducts = productRepository.findAll();
    assertFalse(products.size() == newProducts.size());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete__failure(){
        List<Product> products = productRepository.findAll();
        productRepository.deleteById(10L);
        List<Product> newProducts = productRepository.findAll();
        assertTrue(products.size() == newProducts.size());
    }
}
