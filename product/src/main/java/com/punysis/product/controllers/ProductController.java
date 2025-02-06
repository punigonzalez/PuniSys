package com.punysis.product.controllers;

import com.punysis.product.entity.Product;
import com.punysis.product.entity.ProductRequest;
import com.punysis.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products_list")
    public ResponseEntity<?> getAllProducts(){
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Product> product = productService.getById(id);
        if (product.isPresent()){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        Optional<Product> product = productService.getByName(name);
        if(product.isPresent()){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
    }

    //filter
    //filtrar cliente por nombre
    @GetMapping("/filter/name")
    public ResponseEntity<?> filterProductsByName(@RequestParam("name") String name){
        List<Product> products = productService.filterByName(name);
        if(products.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos.");
        }
        return ResponseEntity.ok(products);
    }

    //filtrar cliente por nombre
    @GetMapping("/filter/brand")
    public ResponseEntity<?> filterProductsByBrand(@RequestParam("brand") String brand){
        List<Product> products = productService.filterByBrand(brand);
        if(products.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos.");
        }
        return ResponseEntity.ok(products);
    }



    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest p){
        Product newProduct = productService.create(p);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProduct.getId())
                .toUri();
        return ResponseEntity.created(location).body(newProduct);
    }

}
