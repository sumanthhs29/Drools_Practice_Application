package com.drools.coupons.controller;

import com.drools.coupons.dto.NewCoupon;
import com.drools.coupons.dto.NewOrder;
import com.drools.coupons.dto.Order;
import com.drools.coupons.entity.Coupon;
import com.drools.coupons.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping("/checkout")
    public NewOrder orderCheckout(@RequestBody Order order){
        return orderService.applyCoupons(order);
    }

    @GetMapping("/addCoupon")
    public NewCoupon addNewCoupon(@RequestBody Coupon coupon){
        return orderService.addingNewCoupons(coupon);
    }

    @GetMapping("/testSpreadSheet")
    public void testSpreadSheet(@RequestBody Order order){
        orderService.testSpreadSheet(order);
    }

}
