package com.jjh.ecommerce.dao;

import com.jjh.ecommerce.dao.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "countries", path="countries")//expose /countries endpoint
public interface CountryRepository extends JpaRepository<Country, Integer> {
    //Contry는 Entity class이고, Integer는 Primary key 이다.
}
