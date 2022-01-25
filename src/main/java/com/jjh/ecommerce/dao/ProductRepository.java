package com.jjh.ecommerce.dao;

import com.jjh.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {//Entity, Primary Key


}
