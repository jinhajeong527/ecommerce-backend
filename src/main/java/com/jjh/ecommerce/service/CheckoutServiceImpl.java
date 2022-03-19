package com.jjh.ecommerce.service;

import com.jjh.ecommerce.dao.CustomerRepository;
import com.jjh.ecommerce.dao.entity.Customer;
import com.jjh.ecommerce.dao.entity.Order;
import com.jjh.ecommerce.dao.entity.OrderItem;
import com.jjh.ecommerce.dto.Purchase;
import com.jjh.ecommerce.dto.PurchaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service // Spring이 컴포넌트 스캐닝 때 픽업할 수 있도록.
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    @Autowired //생성자 하나만 있기 때문에 굳이 필요하진 않다.
    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // dto에서 order info 얻어온다.
        Order order = purchase.getOrder();

        // tracking number generate 한다.
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // orderitems로 order populate
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item)); //람다 사용

        // populate order with billing address and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();

        //존재하는 Customer 인지 확인한다.
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if(customerFromDB != null) {
            //Customer 존재하면
            customer = customerFromDB;
        }

        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        //unique id that is hard to guess and random
        //randon UUID 만들기(UUID version - 4)
        return UUID.randomUUID().toString();
    }
}
