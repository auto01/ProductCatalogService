package com.example.productcatalogservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Double price;
    private CategoryDto category;
}


