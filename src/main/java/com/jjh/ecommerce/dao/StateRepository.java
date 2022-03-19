package com.jjh.ecommerce.dao;

import com.jjh.ecommerce.dao.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StateRepository extends JpaRepository<State, Integer> {//엔티티 클래스와 , primary key
    //country code에 기반하여 states retrieve 한다.
    //http://localhost:8181/api/states/search/findByCountryCode?code=US
    List<State> findByCountryCode(@Param("code") String code);

}
