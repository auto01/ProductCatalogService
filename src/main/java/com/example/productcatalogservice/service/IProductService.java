package com.example.productcatalogservice.service;

import com.example.productcatalogservice.dto.FakeStoreProductDto;
import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    FakeStoreProductDto createProduct(FakeStoreProductDto product);
    FakeStoreProductDto updateProduct(FakeStoreProductDto product, Long id);
}
