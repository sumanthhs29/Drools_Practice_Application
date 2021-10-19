package com.drools.coupons.dto;

import com.drools.coupons.entity.ShippingDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewOrder {
    private List<String> products;
    private ShippingDetails shippingDetails;
    private Integer quantity;
    private Double totalAmount;
    private Double discountPrice;
    private String message;
}
