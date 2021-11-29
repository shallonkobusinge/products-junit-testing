package com.example.product_junit_testing.controller;

import com.example.product_junit_testing.dto.ProductDto;
import com.example.product_junit_testing.model.Product;
import com.example.product_junit_testing.service.ProductService;
import com.example.product_junit_testing.utils.APIResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @MockBean
    private ProductService productServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllSuccess() throws Exception {
        List<Product> list = Arrays.asList(new Product(1L,"Shoes",1000.0,10),new Product(2L,"Clothes",1000.0,10));
        when(productServiceMock.getAll()).thenReturn(list);

        MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders
                .get("/products")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc
                .perform(requestBuilders)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1, \"name\":\"Shoes\", \"price\":1000.0,\"quantity\":10},{\"id\":2, \"name\":\"Clothes\", \"price\":1000.0,\"quantity\":10}]"))
                .andReturn();

    }

    @Test
    public void getById_success() throws Exception {
        Product product = new Product(1L,"Shoes",1000.0,10);
        when(productServiceMock.getById(product.getId())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(new APIResponse(true,"", product)));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/products/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":true,\"message\":\"\",\"data\":{\"id\":1,\"name\":\"Shoes\",\"price\":1000.0,\"quantity\":10}}"))
                .andReturn();

    }

    @Test
    public void getById__failure() throws Exception {
        Product product = new Product(1L,"Shoes",1000.0,10);
        when(productServiceMock.getById(10L)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(false,"Product not found")));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/products/10")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":false,\"message\":\"Product not found\"}"))
                .andReturn();

    }

//    @Test
//    public void registerProduct_Success() throws Exception {
//        Product product = new Product(1L,"Orange", 300.0, 20);
//        ProductDto productDto = new ProductDto("Orange",300.0, 20);
//        when(productServiceMock.save(productDto)).thenReturn(product);
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post("/save-product")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"id\":1L, \"name\":\"Orange\", \"price\":300.0,\"quantity\":20}");
//
//        MvcResult result = mockMvc
//                .perform(request)
//                .andExpect(status().isCreated())
//                .andExpect(content().json("{\"id\":1L, \"name\":\"Orange\", \"price\":300.0,\"quantity\":20}"))
//                .andReturn();
//    }

    @Test
    public void updateProduct_success() throws Exception{
        Product product = new Product(1L,"Clothes",1000.0,10);
        ProductDto productDto = new ProductDto("Shoes",3000.0,30);
        Product updatedProduct = new Product(1L, "Shoes", 3000.0,30);
        when(productServiceMock.update(product.getId(), productDto)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(true,"Product updated successfully",updatedProduct)));
        System.out.println(ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(true,"Product updated successfully",updatedProduct)));
        //
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("{\"name\":\"Shoes\", \"price\":3000.0,\"quantity\":30}");
        MvcResult result = mockMvc
                .perform(request)
//                .andExpect(status().isCreated())
//                .andExpect(content().json("{\"name\":\"Shoes\", \"price\":3000.0,\"quantity\":30}"))
                .andReturn();
        System.out.println(result.getResponse().getStatus());

    }


//    @Test
//    public void create__success() throws Exception{
//        Product product = new Product(1L,"Clothes",1000.0,10);
//        ProductDto productDto = new ProductDto("Clothes",1000.0,10);
////        when(productServiceMock.create(productDto)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(true,"Product created Successfully",product)));
//        when(productServiceMock.save(productDto)).thenReturn(product);
////        System.out.println(productServiceMock.save(productDto));
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post("/products/all")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"id\":1,\"name\":\"Clothes\",\"price\":1000.0,\"quantity\":10}");
////        System.out.println(request);
//
//        MvcResult result = mockMvc
//                .perform(request)
//                .andExpect(status().isCreated())
////                .andExpect(content().json("{\"status\":true,\"message\":\"Product created successfully\",\"data\":{\"id\":1,\"name\":\"Clothes\",\"price\":1000.0,\"quantity\":10}}"))
//                .andReturn();
//        System.out.println("reesult "+ result.getResponse().getStatus());
//    }


}
