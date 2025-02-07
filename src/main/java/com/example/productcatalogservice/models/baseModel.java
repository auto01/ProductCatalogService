package com.example.productcatalogservice.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class baseModel implements Serializable {
    @Id
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private State state;
}
