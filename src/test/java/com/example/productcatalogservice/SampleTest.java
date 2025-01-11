package com.example.productcatalogservice;

import com.example.productcatalogservice.controllers.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SampleTest {

    @Test
    public void addNewProduct() {
        //AAA (Arrange,Act,Assert)
        int productId = 0; //Arrange

        productId++; // act

        assertEquals(productId,3);
    }
}
