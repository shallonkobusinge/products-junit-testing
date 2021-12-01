package com.example.product_junit_testing.IntegrationTest;

import com.example.product_junit_testing.dto.ProductDto;
import com.example.product_junit_testing.model.Product;
import com.example.product_junit_testing.utils.APIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
//import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final ObjectMapper om = new ObjectMapper();


    @Test
    public void getAll_Success() throws JSONException {
        String response = this.testRestTemplate.getForObject("/products",String.class);
        JSONAssert.assertEquals("[{\"id\":1,\"name\":\"NIkes\",\"price\":10000.0,\"quantity\":20},{\"id\":2,\"name\":\"T-shirts\",\"price\":30000.0,\"quantity\":10},{\"id\":3,\"name\":\"Earphones\",\"price\":8000.0,\"quantity\":7}]", response,false);
    }

    @Test
    public void getById__success() throws Exception {
        String expected = "{\"status\":true,\"message\":\"\",\"data\":{\"id\":1,\"name\":\"NIkes\",\"price\":10000.0,\"quantity\":20}}";
        ResponseEntity<String> response =this.testRestTemplate.withBasicAuth("spring","secret").getForEntity("/products/1", String.class);
//                this.testRestTemplate.getForEntity("/products/1",String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void getById__404() throws Exception{
        String expected = "{\"status\":false,\"message\":\"Product not found\",\"data\":null}";
        ResponseEntity<String>  response = this.testRestTemplate.getForEntity("/products/10",String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void saveProduct__success() throws Exception{
        ProductDto productDto = new ProductDto("Monk", 300.0, 20);
        Product product = new Product(10L,productDto);
        ResponseEntity<Product> responseEntity = this.testRestTemplate.postForEntity("/products", product, Product.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
//        assertEquals(300.0, responseEntity.getBody().getPrice());
    }

    @Test
    public void saveProduct__BadRequest(){
        ProductDto productDto = new ProductDto("Earphones", 300.0, 20);
        Product product = new Product(6L,productDto);
        ResponseEntity<Product> responseEntity = this.testRestTemplate.postForEntity("/products", product, Product.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
//        assertEquals("Product name already exists", Objects.requireNonNull(responseEntity.getBody()).getMessage());

    }

    @Test
    public void updateProduct__success() throws JsonProcessingException {

        Product UpdatedProduct = new Product(1L,"Canvas", 300.0, 20);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(UpdatedProduct), headers);
        ResponseEntity<String> response = this.testRestTemplate.exchange("/products/1", HttpMethod.PUT,entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void deleteProduct__success(){
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = this.testRestTemplate.exchange("/products/1", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }



}
