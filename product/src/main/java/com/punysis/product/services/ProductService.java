package com.punysis.product.services;
import com.punysis.product.entity.Product;
import com.punysis.product.entity.ProductRequest;
import com.punysis.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate; // Para llamar al microservicio de supplier


    private static final String SUPPLIER_SERVICE_URL = "http://localhost:8080/suppliers";

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id){
        return productRepository.findById(id);
    }


    public Product createProduct(ProductRequest productRequest) {
        // Verificar si el supplier existe
        Long supplierId = productRequest.getSupplierId();
        ResponseEntity<Supplier> response = restTemplate.getForEntity(
                SUPPLIER_SERVICE_URL + "/" + supplierId, Supplier.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // Crear el producto y vincularlo al supplier
            Product product = new Product();
            product.setName(productRequest.getName());
            product.setSupplierID(supplierId);
            return productRepository.save(product);
        } else {
            // Supplier no existe
            return null;
        }
    }

}
