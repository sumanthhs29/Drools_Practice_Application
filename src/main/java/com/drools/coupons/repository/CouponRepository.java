package com.drools.coupons.repository;

import com.drools.coupons.entity.Coupon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Integer> {
}
