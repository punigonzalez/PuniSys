package com.punysis.supplier.repository;
import com.punysis.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    Optional<Supplier> findByname(String name);
    Optional<Supplier> findByEmail(String email);
    Optional<Supplier> findByPhone(String phone);

}
