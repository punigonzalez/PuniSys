package com.punysis.supplier.controllers;

import com.punysis.supplier.entity.Supplier;
import com.punysis.supplier.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    SupplierService supplierService;


    @GetMapping("/suppliers_list")
    public ResponseEntity<?> getAllSuppliers(){
        List<Supplier> suppliers = supplierService.getAll();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Supplier> supplier = supplierService.getById(id);
        if (supplier.isPresent()){
            return ResponseEntity.ok(supplier);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado.");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        Optional<Supplier> supplier = supplierService.getByName(name);
        if(supplier.isPresent()){
            return ResponseEntity.ok(supplier);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado.");
    }


    @PostMapping
    public ResponseEntity<?> createSupplier(@RequestBody Supplier s){
        Supplier newSupplier = supplierService.create(s);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(s.getId())
                .toUri();
        return ResponseEntity.created(location).body(newSupplier);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody Supplier s){
        Optional<Supplier> oldSupplier = supplierService.getById(id);

        if(oldSupplier.isPresent()){
        Supplier currentSupplier = oldSupplier.get();
        currentSupplier.setName(s.getName());
        currentSupplier.setPhone(s.getPhone());
        currentSupplier.setEmail(s.getEmail());

        Supplier updatedSupplier =  supplierService.create(currentSupplier);
        return ResponseEntity.ok(updatedSupplier);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<Supplier> s = supplierService.getById(id);
        if(s.isPresent()){
            supplierService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado.");
    }










}
