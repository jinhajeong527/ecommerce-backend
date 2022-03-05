package com.jjh.ecommerce.dto;

import lombok.Data;

@Data
public class PurchaseResponse {
    //java object를 json으로 돌려보내기 위해 사용할 클래스
    private final String orderTrackingNumber;

    /*
     final 대신 쓸 수 있는 방법
     @NonNull
     private String orderTrackingNumber;
     */
}