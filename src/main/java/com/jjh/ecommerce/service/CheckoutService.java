package com.jjh.ecommerce.service;

import com.jjh.ecommerce.dto.Purchase;
import com.jjh.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

}
