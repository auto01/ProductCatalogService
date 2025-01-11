package com.example.productcatalogservice.repository;

import com.example.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(Long id);

    @Override
    Product save(Product product);

    @Override
    List<Product> findAll();


}
