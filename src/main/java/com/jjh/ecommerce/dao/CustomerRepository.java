package com.jjh.ecommerce.dao;

import com.jjh.ecommerce.dao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // <Customer, Long> : Customer는 엔티티타입이고, Long은 프라이머리 키 타입니다.
    // Customer를 사용하는 이유는, Collection of Orders를 갖고 있기 때문이다.
    // Purchase가 오면, Customer 사용해서 적절하게 모은 후에 Customer 저장할 것이다.

    Customer findByEmail(String theEmail);
    // Spring은 SELECT * FROM Coutomer c WHERE c.eamil = theEamil 과 유사한 쿼리 실행할 것이다.
    // method는 Customer 찾지 못하면 return null 할 것이다.
}
