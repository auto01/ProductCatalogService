package com.example.productcatalogservice.service;

import com.example.productcatalogservice.dto.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("f1")
public class FakeStoreProductService implements IProductService{

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getAllProducts() {

        RestTemplate restTemplate = restTemplateBuilder.build();
        List<Product> products=new ArrayList<>();
        ResponseEntity<FakeStoreProductDto[]> responses = restTemplate.getForEntity(
                "http://fakestoreapi.com/products/",FakeStoreProductDto[].class);
        for(FakeStoreProductDto fakeStoreProductDto:responses.getBody()){
            products.add(from(fakeStoreProductDto));
        }

        return products;
    }

    @Override
    public Product getProductById(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto response=restTemplate.getForObject("http://fakestoreapi.com/products/{productId}", FakeStoreProductDto.class, productId);
        if(response!=null){
            return from(response);
        }
        return null;
    }

    @Override
    public Product replaceProductById(Long productId, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoRequest = from(product);
        FakeStoreProductDto response = requestForEntity("http://fakestoreapi.com/products/{productId}",
                HttpMethod.PUT,fakeStoreProductDtoRequest, FakeStoreProductDto.class,productId).getBody();
        return from(response);
    }

    @Override
    public Product save(Product product) {
        return null;
    }


    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }


    public FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImgUrl());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        return fakeStoreProductDto;
    }
    public Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImgUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
