package com.punysis.product.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 @Data @AllArgsConstructor @NoArgsConstructor
public class SupplierDTO {
    private Long id;
    private String name;
}
