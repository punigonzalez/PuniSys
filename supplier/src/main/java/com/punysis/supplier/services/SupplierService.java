package com.punysis.supplier.services;
import com.punysis.supplier.entity.Supplier;
import com.punysis.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> getAll(){
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getById(Long id){
        return supplierRepository.findById(id);
    }

    public Optional<Supplier> getByName(String name){
        return supplierRepository.findByname(name);
    }

    public Optional<Supplier> getByEmail(String email){
        return supplierRepository.findByEmail(email);
    }

    public Optional<Supplier> getByPhone(String phone){
        return supplierRepository.findByPhone(phone);
    }

    public Supplier create(Supplier s){
        if(supplierRepository.findByname(s.getName()).isPresent()){
            throw new IllegalArgumentException("El proveedor ya está registrado.");
        }
        if(supplierRepository.findByEmail(s.getEmail()).isPresent()){
            throw new IllegalArgumentException("El mail ya está registrado.");
        }
        return supplierRepository.save(s);
    }

    public void deleteByid(Long id){
         supplierRepository.deleteById(id);
    }










}
