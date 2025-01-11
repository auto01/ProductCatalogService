package com.example.productcatalogservice.service;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service("f2")
public class FakeStoreStorageService implements IProductService{

    @Autowired
    ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {
        //return List.of();
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product=productRepo.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product replaceProductById(Long id, Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product save(Product product) {
        System.out.println("Saving product....");
        return productRepo.save(product);
    }


}
