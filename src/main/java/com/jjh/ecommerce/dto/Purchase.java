package com.jjh.ecommerce.dto;

import com.jjh.ecommerce.dao.entity.Address;
import com.jjh.ecommerce.dao.entity.Customer;
import com.jjh.ecommerce.dao.entity.Order;
import com.jjh.ecommerce.dao.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
