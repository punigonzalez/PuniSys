package com.punysis.customer.controllers;

import com.punysis.customer.entities.Customer;
import com.punysis.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class CustomerController {

   @Autowired
   CustomerService customerService;


    // crear cliente y generar uri
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer c){
        Customer newCustomer = customerService.create(c);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(c.getId())
                .toUri();
        return ResponseEntity.created(location).body(newCustomer);
    }


    // devolver todos los clientes
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAll();
    }





}
