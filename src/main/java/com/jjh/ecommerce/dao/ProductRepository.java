package com.jjh.ecommerce.dao;

import com.jjh.ecommerce.dao.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product,Long> {//Entity, Primary Key
    Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable);
    //스프링은 select * from product where category_id=? 이 쿼리 실행할 것이다.

    Page<Product> findByNameContaining(@RequestParam("name") String name, Pageable pageable);
    //select * from product p where p.name like concat(’%’, :name , ‘%’) 와 유사 한 쿼리 실행할 것이다.
}
