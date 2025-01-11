package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.service.IProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    ProductController productController;

    @MockitoBean
    @Qualifier("f2")
    IProductService productService;


    @Test
    void getProducts() {

        // Arrange
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        Category category = new Category();
        category.setId(2L);
        category.setName("Category 1");
        product.setCategory(category);
        products.add(product);

        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductDto>> responseEntity = productController.getProducts();
        List< ProductDto> response = responseEntity.getBody();

        // Assert
        assertEquals(products.size(), response.size());


    }

    @Test
    void getProductbyId() {

        // positive test case

        //Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        Category category = new Category();
        category.setId(2L);
        category.setName("Category 1");
        product.setCategory(category);

        when(productService.getProductById(1L)).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> responseEntity=productController.getProductbyId(product.getId()+1);

        //Assert
        // verify that productid returned by controller is same as input
        assertEquals(product.getId(),responseEntity.getBody().getId(),"Input ProductId doesn't match Output Product");


    }

    @Test
    void updateProduct() {
    }

    @Test
    void createProduct() {
    }
}