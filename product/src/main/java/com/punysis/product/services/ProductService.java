package com.punysis.product.services;

import com.punysis.product.controllers.ProductController;
import com.punysis.product.entity.Product;
import com.punysis.product.entity.ProductRequest;
import com.punysis.product.entity.SupplierDTO;
import com.punysis.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    private static final String SUPPLIER_SERVICE_URL = "http://localhost:8081/suppliers";

    @Autowired
    public ProductService(ProductRepository productRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getByName(String name){
        return productRepository.findByName(name);
    }



    public Product create(ProductRequest productRequest) {
        Long supplierId = productRequest.getSupplierId();

        try {
            // Llamar al otro microservicio
            ResponseEntity<SupplierDTO> response = restTemplate.getForEntity(
                    SUPPLIER_SERVICE_URL + "/" + supplierId, SupplierDTO.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                SupplierDTO supplier = response.getBody();

                // Crear el producto y vincularlo al supplier
                Product product = new Product();
                product.setName(productRequest.getName());
                product.setSupplierID(supplier.getId()); // Guardamos solo el ID

                return productRepository.save(product);
            } else {
                System.out.println("Supplier no encontrado: " + supplierId);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al llamar al microservicio de Supplier: " + e.getMessage());
            return null;
        }
    }


    //filter
    public List<Product> filterByName(String name){
        return productRepository.filterByName(name);
    }

    public List<Product> filterByBrand(String brand){
        return productRepository.filterByBrand(brand);
    }

   // public List<Product> filterBySupplier(Long id){}



}
