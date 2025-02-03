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


    public List<Customer> getAll(Long id){
        return customerRepository.findAll();
    }

    public Customer create(Customer c){
        return customerRepository.save(c);
    }

    public Optional<Customer> getById(Long id){
       return customerRepository.findById(id);
    }

    public Optional<Customer> getByUsername(String username){
        return customerRepository.findUsername(username);
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
    public List<Customer> filterUsername(String username){
        return customerRepository.filterByName(username);
    }

}
