package com.drools.coupons.services;

import com.drools.coupons.entity.Coupon;

import java.time.LocalDate;
import java.util.List;

public class UtilService {
    public UtilService() {}

    public Boolean isCoupon(List<Coupon>allCoupons, String name){
        System.out.println(allCoupons);
        System.out.println(name);
        for (Coupon c :allCoupons){
            if(c.getCouponName().equals(name)){
                if(c.getStartDate().isBefore(LocalDate.now()) && c.getEndDate().isAfter(LocalDate.now()))
                    return true;
                return false;
            }
        }
        return false;
    }
}
