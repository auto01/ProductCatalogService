package com.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class Product extends baseModel{

    private String name;
    private String description;
    private String imgUrl;
    private Double price;
    private Category category;
    private Boolean isPrime;
}
