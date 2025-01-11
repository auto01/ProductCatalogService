package com.example.productcatalogservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends baseModel{
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // mappedBy helps to avoid given relationship
    // this tells JPA to not consider this 1:M because it is already declared for Product:Category=(M:1)
    // creating again will lead to M:M mapping , which we don't want

    public Category() {
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
        this.setState(State.ACTIVE);
    }
}

