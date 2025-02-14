package com.punysis.customer.controllers;

import com.punysis.customer.entities.Customer;
import com.punysis.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
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

    // devolver cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCutomerById(@PathVariable Long id){
        if (id ==null || id < 0 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ingresar un id valido.");
        }
        Optional<Customer> c = customerService.getById(id);
        if(!c.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
        }
        return ResponseEntity.ok(c);
    }

    //devoler cliente por username
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getCustomerByUsername(@PathVariable String username){
        Optional<Customer> c = customerService.getByUsername(username);
        if (!c.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
        }
        return ResponseEntity.ok(c);
    }

    //filtrar cliente por nombre
    @GetMapping("/filter")
    public ResponseEntity<?> filterCustomersByName(@RequestParam("name") String name){
        List<Customer> customers = customerService.filterByName(name);
        if(customers.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron clientes.");
        }
        return ResponseEntity.ok(customers);
    }

    //actualizar cliente por id con patch
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateCustomerById(@PathVariable Long id, @RequestBody Customer c){
        if(id==null || id<0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingresar un id valido.");
        }
        Optional<Customer> oldCustomer = customerService.getById(id);
        if (oldCustomer.isPresent()){
            Customer currentCustomer = oldCustomer.get();
            currentCustomer.setName(c.getName());
            currentCustomer.setLastname(c.getLastname());
            currentCustomer.setUsername(c.getUsername());
            currentCustomer.setEmail(c.getEmail());
            currentCustomer.setCountry(c.getCountry());
            currentCustomer.setPhone(c.getPhone());

            Customer updatedCustomer = customerService.create(currentCustomer);
            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
    }


    // eliminar cliente por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id){
        if(id==null || id<0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingresar un id valido.");
        }
        customerService.deleteById(id);
        return ResponseEntity.ok().build();
    }







}
