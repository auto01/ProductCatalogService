package com.example.productcatalogservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
public class Product extends baseModel{

    private String name;
    @Column(length = 1000)
    private String description;
    private String imgUrl;
    private Double price;
    @ManyToOne(cascade = CascadeType.ALL) // if category is deleted then all products of it will be deleted
    private Category category;
    private Boolean isPrime;

    public Product() {
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
        this.setState(State.ACTIVE);

    }
}
