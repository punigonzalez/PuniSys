package com.punysis.customer.services;

import com.punysis.customer.entities.Customer;
import com.punysis.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;



    // crea el usuario sin que se repita el mail
    public Customer create(Customer c){
        if (customerRepository.findByEmail(c.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        if (customerRepository.findByPhone(c.getPhone()).isPresent()) {
            throw new IllegalArgumentException("El teléfono ya está registrado.");
        }
        if (customerRepository.findByUsername(c.getUsername()).isPresent()){
            throw new IllegalArgumentException("El usuario ya esta registrado.");
        }
        return customerRepository.save(c);
    }

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getById(Long id){
       return customerRepository.findById(id);
    }

    public Optional<Customer> getByUsername(String username){
        return customerRepository.findByUsername(username);
    }

    public void deleteById(Long id){
        customerRepository.deleteById(id);
    }
    public void deleteByUsername(String username){
        customerRepository.deleteByUsername(username);
    }

    public Customer update(Customer c){
        return customerRepository.save(c);
    }

    //filter
    public List<Customer> filterByName(String name){
        return customerRepository.filterByName(name);
    }

}
