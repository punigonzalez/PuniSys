package com.punysis.customer.repository;

import com.punysis.customer.CustomerApplication;
import com.punysis.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository <Customer,Long>{

    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> filterByName(@Param("name") String name);

    Optional<Customer> findUsername(String username);

    void deleteByUsername(String username);



}
