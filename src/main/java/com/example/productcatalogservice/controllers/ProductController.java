package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.commons.AuthenticationCommon;
import com.example.productcatalogservice.dto.CategoryDto;
import com.example.productcatalogservice.dto.ProductDto;
import com.example.productcatalogservice.dto.UserDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class ProductController {

    @Autowired
    @Qualifier("f1")
    private IProductService productService;

    @Autowired
    @Qualifier("f2")
    private IProductService productService2;

    @Autowired
    private AuthenticationCommon authenticationCommon;

    @GetMapping("/products/all/{token}")
    public ResponseEntity<List<ProductDto>> getProducts(@PathVariable("token") String token) {
        System.out.println("getProducts all called");
        System.out.println("token: " + token);

        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
        // validate token first
        System.out.println("Not validating token for now..");
        /*
        try {
            UserDto userDto=authenticationCommon.validateToken(token);
            if(userDto==null){
                headers.add("error", "Invalid token");
                return new ResponseEntity<>(null,headers,HttpStatus.UNAUTHORIZED);
            }
            System.out.println("valid user :"+userDto.getEmail());
        } catch (Exception e) {
            headers.add("error", "Something went wrong");
            return new  ResponseEntity<>(null,headers,HttpStatus.INTERNAL_SERVER_ERROR);
        }

         */
        //get product list
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productService2.getAllProducts();
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

    @GetMapping("/products/{id}/{token}")
    public ResponseEntity<ProductDto> getProductbyId(@PathVariable Long id, @PathVariable String token) {

        System.out.println("token: " + token);


        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();

        // validate token first
        System.out.println("Not validating token for now..");
        /*
        try {
            UserDto userDto=authenticationCommon.validateToken(token);
            if(userDto==null){
                headers.add("error", "Invalid token");
                return new ResponseEntity<>(null,headers,HttpStatus.UNAUTHORIZED);
            }
            System.out.println("valid user :"+userDto.getEmail());
        } catch (Exception e) {
            headers.add("error", "Something went wrong");
            return new  ResponseEntity<>(null,headers,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        */

        // get product details
        if(id<0){
            headers.add("status", "Product id cannot be negative");
            return new ResponseEntity<>(null,headers,HttpStatus.BAD_REQUEST);
        }





        Product product=productService2.getProductById(id);
        System.out.println("product="+product);
        if(product==null){
            headers.add("status", "Product not found");
            return new ResponseEntity<>(null,headers,HttpStatusCode.valueOf(404));
        }
        headers.add("success", "Product found");
        return new ResponseEntity<>(from(product),headers, HttpStatusCode.valueOf(200));
    }


    @PutMapping("/products/{product_id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long product_id,@RequestBody  ProductDto productDto) {
        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();

        Product product=productService2.replaceProductById(product_id,from(productDto));
        System.out.println("product="+product);
        if(product==null){
            headers.add("status", "Product not updated");
            return new ResponseEntity<>(null,headers,HttpStatus.NOT_ACCEPTABLE);
        }
        headers.add("status", "Product updated");
        return new ResponseEntity<>(from(product),headers, HttpStatus.CREATED);
    }

    @PostMapping("/product")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product input = from(productDto);
        Product output = productService2.save(input);
        return from(output);
    }

    private ProductDto from(Product p) {
        ProductDto productDto=new ProductDto();
        productDto.setId(p.getId());
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setName(p.getCategory().getName());
        categoryDto.setDescription(p.getCategory().getDescription());
        categoryDto.setId(p.getCategory().getId());

        productDto.setCategory(categoryDto);
        productDto.setDescription(p.getDescription());
        productDto.setPrice(p.getPrice());
        productDto.setImgUrl(p.getImgUrl());
        productDto.setName(p.getName());

        return productDto;
        //return null;
    }
    private Product from(ProductDto productDto) {
        Product product=new Product();
        product.setId(productDto.getId());

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImgUrl(productDto.getImgUrl());

        Category category=new Category();
        category.setName(productDto.getCategory().getName());
        category.setDescription(productDto.getCategory().getDescription());
        category.setId(productDto.getCategory().getId());

        product.setCategory(category);

        return product;
    }

}
