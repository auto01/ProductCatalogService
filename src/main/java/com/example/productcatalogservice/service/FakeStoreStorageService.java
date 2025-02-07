package com.example.productcatalogservice.service;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service("f2")
public class FakeStoreStorageService implements IProductService{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {
        //return List.of();
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {

        String hash_key="PRODUCTS";
        // search in cache
        Product p=(Product) redisTemplate.opsForHash().get(hash_key,id.toString());
        if(p!=null){
            //cache hit
            return p;
        }
        //cache miss
        Optional<Product> product=productRepo.findById(id);
        if(product.isPresent()){
            redisTemplate.opsForHash().put(hash_key,id.toString(),product.get());
            redisTemplate.expire(hash_key, 1, TimeUnit.MINUTES);

        }
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
