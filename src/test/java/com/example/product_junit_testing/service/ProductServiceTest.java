package com.example.product_junit_testing.service;

import com.example.product_junit_testing.dto.ProductDto;
import com.example.product_junit_testing.model.Product;
import com.example.product_junit_testing.repository.ProductRepository;
import com.example.product_junit_testing.utils.APIResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @Test
    public void getAll(){
        List<Product> product = Arrays.asList(new Product(1L,"SHoes",1000.0,10),new Product(2L,"Laptops",1000.0,10));
        when(productRepositoryMock.findAll()).thenReturn(product);
        assertEquals(productService.getAll().get(0), product.get(0));
        assertTrue(productService.getAll().size() == product.size());

    }

    @Test
    public void  getOneById__success(){
        Product product = new Product(1L,"SHoes",1000.0,10);
        when(productRepositoryMock.findById(10L)).thenReturn(Optional.of(product));
        assertEquals(productService.getById(10L).getBody().getData(), product);
    }

    @Test
    public void getOneById__failure(){
        Optional emptyOptional = Optional.empty();
        when(productRepositoryMock.findById(10L)).thenReturn(emptyOptional);
        assertEquals(productService.getById(10L).getBody().isStatus(), false);
        assertEquals(productService.getById(10L).getStatusCode() == HttpStatus.NOT_FOUND,true);
    }

   @Test
    public void create__success(){
       ProductDto productDto = new ProductDto("Mouse",10000.0,10);
       Product product = new Product(10L,productDto);
       when(productRepositoryMock.save(any(Product.class))).thenReturn(product);
       assertNotNull(productService.create(productDto).getBody().getData().getId());
       assertEquals(productDto.getQuantity(), productService.create(productDto).getBody().getData().getQuantity());
       assertTrue(productService.create(productDto).getStatusCodeValue() == 201);
   }

    @Test
    public void create__failure(){
        ProductDto productDto = new ProductDto("Mouse",10000.0,10);
        Product product = new Product(10L,productDto);
        when(productRepositoryMock.save(any(Product.class))).thenReturn(product);
        when(productRepositoryMock.existsByName(product.getName())).thenReturn(true);
        assertTrue(productService.create(productDto).getBody().isStatus() == false);
        assertFalse(productService.create(productDto).getStatusCode().is2xxSuccessful());
    }

    @Test
    public void update__success(){
        ProductDto productDto = new ProductDto("Mouse",10000.0,10);

        Product product = new Product(10L,"Clothes",10000.0,20);
        when(productRepositoryMock.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepositoryMock.existsByName(product.getName())).thenReturn(false);
        when(productRepositoryMock.save(product)).thenReturn(product);
        ResponseEntity<APIResponse> updatedProduct = productService.update(10L,productDto);
        assertTrue(updatedProduct.getBody().getData().getName() == productDto.getName());
        assertFalse(updatedProduct.getBody().getData().getName() != product.getName());
        assertEquals(updatedProduct.getBody().isStatus() , true);

    }

    @Test(expected = NullPointerException.class)
    public void update__failure(){
        ProductDto productDto = new ProductDto("Earphones",10000.0,10);
        Product product = new Product(10L,"Mouse",10000.0,20);
        when(productRepositoryMock.findById(10L)).thenReturn(Optional.of(product));
        when(productRepositoryMock.existsByName(productDto.getName())).thenReturn(true);
//        when(productRepositoryMock.save(product)).thenReturn(product);
        ResponseEntity<APIResponse> updatedProduct = productService.update(10L, productDto);

        assertFalse(updatedProduct.getBody().getData().getName() == productDto.getName());
        assertEquals(updatedProduct.getBody().isStatus(), false);
        assertTrue(updatedProduct.getStatusCodeValue()==400);

    }

    @Test
    public void delete__success(){

        Product product = new Product(10L,"Mouse",10000.0,20);
        when(productRepositoryMock.findById(product.getId())).thenReturn(Optional.of(product));
        productService.delete(product.getId());
        verify(productRepositoryMock).deleteById(product.getId());
    }

}
