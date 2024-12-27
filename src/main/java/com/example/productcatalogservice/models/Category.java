package com.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Category extends baseModel{
    private String name;
    private String description;
    private List<Product> products;
}
