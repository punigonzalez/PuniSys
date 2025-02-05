package com.punysis.product.services;
import com.punysis.product.entity.Product;
import com.punysis.product.repository.ProductRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRespository productRespository;

    private static final String SUPPLIER_SERVICE_URL = "http://localhost:8080/suppliers";

    public List<Product> getAll(){
        return productRespository.findAll();
    }

    public Optional<Product> getById(Long id){
        return productRespository.findById(id);
    }



}
