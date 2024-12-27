package com.example.productcatalogservice.service;

import com.example.productcatalogservice.dto.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImgUrl(fakeStoreProductDto.getImageUrl());
        Category category=new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        product.setPrice(Double.valueOf(fakeStoreProductDto.getPrice()));
        return product;
    }


    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> listResponseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);
        for(FakeStoreProductDto fakeStoreProductDto : listResponseEntity.getBody()){
            products.add(from(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product getProductById(Long productId) {
            RestTemplate restTemplate = restTemplateBuilder.build();
            ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity= restTemplate.getForEntity(
                            "https://fakestoreapi.com/products/{prdocutId}",
                            FakeStoreProductDto.class, productId);
            System.out.println("status"+fakeStoreProductDtoResponseEntity.getStatusCode());
            System.out.println(fakeStoreProductDtoResponseEntity.getBody());
            if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDtoResponseEntity.getBody()!=null){
                return from(fakeStoreProductDtoResponseEntity.getBody());
            }
            return null;
        }

    @Override
    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity= restTemplate.postForEntity(
                "https://fakestoreapi.com/products",fakeStoreProductDto,FakeStoreProductDto.class);

        System.out.println("status"+fakeStoreProductDtoResponseEntity.getStatusCode());
        System.out.println(fakeStoreProductDtoResponseEntity.getBody());
        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDtoResponseEntity.getBody()!=null){
            //return from(fakeStoreProductDtoResponseEntity.getBody());
            return fakeStoreProductDtoResponseEntity.getBody();
        }
        return null;
    }

    @Override
    public FakeStoreProductDto updateProduct(FakeStoreProductDto product,Long productId) {
        System.out.println("productId:"+productId);
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url="https://fakestoreapi.com/products/{prdocutId}";
        // we can use restTemplate.exchange for every call POST/PUT/GET/..
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity=restTemplate.exchange(url,
                HttpMethod.PUT,new HttpEntity<>(product),FakeStoreProductDto.class,productId);
        System.out.println("status"+fakeStoreProductDtoResponseEntity.getStatusCode());
        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDtoResponseEntity.getBody()!=null){
            return fakeStoreProductDtoResponseEntity.getBody();
        }
        return null;
    }
}

