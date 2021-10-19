package com.drools.coupons.services;

import com.drools.coupons.dto.NewCoupon;
import com.drools.coupons.dto.NewOrder;
import com.drools.coupons.dto.Order;
import com.drools.coupons.entity.Coupon;
import com.drools.coupons.entity.Customer;
import com.drools.coupons.entity.Products;
import com.drools.coupons.entity.ShippingDetails;
import com.drools.coupons.enums.Category;
import com.drools.coupons.enums.DeliveryPreference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testApplyCouponsOne(){
        List<String> test=new ArrayList<>();
        NewOrder newOrder = orderService.applyCoupons(new Order(Arrays.asList(new Products(1,"Laptops", Category.ELECTRONICS,"Work",1,600)),
                new ShippingDetails(1,new Customer(1,"Chetan","Gold",test,0),
                        "Agra", DeliveryPreference.BEST_EFFORT),
                Arrays.asList(new Coupon(1,"20POFF", LocalDate.MIN,LocalDate.MAX,20,0)),
                new Customer(1,"Chetan","Gold",test,0),
                600,0));

        Assertions.assertEquals(120,newOrder.getDiscountPrice());
        Assertions.assertEquals(480,newOrder.getTotalAmount());
        Assertions.assertEquals("[20POFF] Coupons applied",newOrder.getMessage());
    }


    @Test
    public void testApplyCouponsTwo(){
        List<String> test=new ArrayList<>();
        NewOrder newOrder = orderService.applyCoupons(new Order(Arrays.asList(new Products(1,"Laptops", Category.ELECTRONICS,"Work",1,600)),
                new ShippingDetails(1,new Customer(2,"Gaurav","Gold",test,0),
                        "Agra", DeliveryPreference.BEST_EFFORT),
                Arrays.asList(new Coupon(1,"NEW50OFF", LocalDate.of(2021,1,1),LocalDate.of(2022,1,1),0,50)),
                new Customer(2,"Gaurav","Gold",test,0),
                1000,0));

        Assertions.assertEquals(50,newOrder.getDiscountPrice());
        Assertions.assertEquals(950,newOrder.getTotalAmount());
        Assertions.assertEquals("[NEW50OFF] Coupons applied",newOrder.getMessage());
    }


    @Test
    public void testApplyCouponsThree(){
        List<String> test=new ArrayList<>();
        NewOrder newOrder = orderService.applyCoupons(new Order(Arrays.asList(new Products(1,"All about JAVA", Category.BOOKS,"Work",1,600),
        new Products(2,"Database Management", Category.BOOKS,"Work",1,300),
                new Products(3,"Data Structure", Category.BOOKS,"Work",1,400)),
                new ShippingDetails(1,new Customer(3,"Pal","Gold",test,0),
                        "Agra", DeliveryPreference.BEST_EFFORT),
                Arrays.asList(new Coupon(1,"BUY2GET50POFF", LocalDate.of(2021,1,1),LocalDate.of(2022,1,1),50,0)),
                new Customer(3,"Pal","Gold",test,0),
                1000,0));

        Assertions.assertEquals(150,newOrder.getDiscountPrice());
        Assertions.assertEquals(1150,newOrder.getTotalAmount());
        Assertions.assertEquals("[BUY2GET50POFF] Coupons applied",newOrder.getMessage());
    }

    @Test
    public void testAddingCoupon(){

        NewCoupon newCoupon = orderService.addingNewCoupons(new Coupon(4,"GET10POFF",LocalDate.now(),
                LocalDate.of(2022,1,1),10,0));

        Assertions.assertEquals("GET10POFF",newCoupon.getCouponCode());
        Assertions.assertEquals("GET10POFF Added successfully, it is now open to avail",newCoupon.getMessage());
    }
}