package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dto.CategoryDto;
import com.example.productcatalogservice.dto.FakeStoreProductDto;
import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class productController {

    @Autowired
    private IProductService productService;


    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductDto>> getProducts() {
        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        if (products == null) {
            headers.add("status","No products found");
            return new ResponseEntity<>(null,headers,HttpStatus.NO_CONTENT);
        }
        for (Product product : products) {
            productDtos.add(from(product));
        }
        headers.set("status", "list of products");
        return new ResponseEntity<>(productDtos, headers, HttpStatus.OK);
    }

    @GetMapping("/getProducts/{id}")
    public ResponseEntity<ProductDto> getProductbyId(@PathVariable Long id) {
        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
        if(id<0){
            headers.add("status", "Product id cannot be negative");
            return new ResponseEntity<>(null,headers,HttpStatus.BAD_REQUEST);
        }
        Product product=productService.getProductById(id);
        System.out.println("product="+product);
        if(product==null){
            headers.add("status", "Product not found");
            return new ResponseEntity<>(null,headers,HttpStatusCode.valueOf(404));
        }
        headers.add("success", "Product found");
        return new ResponseEntity<>(from(product),headers, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/createProducts")
    public ResponseEntity<FakeStoreProductDto> createProduct(@RequestBody  FakeStoreProductDto p) {
        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
        FakeStoreProductDto product=productService.createProduct(p);
        System.out.println("product="+product);
        if(product==null){
            headers.add("status", "Product not added");
            return new ResponseEntity<>(null,headers,HttpStatus.NOT_ACCEPTABLE);
        }
        headers.add("status", "Product added");
        return new ResponseEntity<>(product,headers, HttpStatus.CREATED);
    }

    @PostMapping("/updateProducts/{product_id}")
    public ResponseEntity<FakeStoreProductDto> updateProduct(@PathVariable Long product_id,@RequestBody  FakeStoreProductDto p) {
        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
        FakeStoreProductDto product=productService.updateProduct(p,product_id);
        System.out.println("product="+product);
        if(product==null){
            headers.add("status", "Product not updated");
            return new ResponseEntity<>(null,headers,HttpStatus.NOT_ACCEPTABLE);
        }
        headers.add("status", "Product updated");
        return new ResponseEntity<>(product,headers, HttpStatus.CREATED);
    }


    private ProductDto from(Product p) {
        ProductDto productDto=new ProductDto();
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setCategoryName(p.getCategory().getName());
        productDto.setCategory(categoryDto);
        productDto.setDescription(p.getDescription());
        productDto.setPrice(p.getPrice());
        productDto.setImgUrl(p.getImgUrl());
        productDto.setName(p.getName());
        return productDto;
    }

}
