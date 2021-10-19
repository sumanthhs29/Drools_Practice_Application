package com.drools.coupons.dto;

import com.drools.coupons.entity.Coupon;
import com.drools.coupons.entity.Customer;
import com.drools.coupons.entity.Products;
import com.drools.coupons.entity.ShippingDetails;
import com.drools.coupons.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order {

    private List<Products> products; //[books,laptops]
    private ShippingDetails shippingDetails;
    private List<Coupon> coupons;
    private Customer customer;
    private double totalAmount;
    private double discountPrice;

    public int getCouponCount() {
        int count=0;
        for (Coupon c:coupons) {
            count++;
        }
        return count;
    }

    public void setDiscount(double d) {
        this.totalAmount-=d;
    }

    public int bookCount() {
        return  (int) products.stream().filter(quantity->quantity.getCategory()== Category.BOOKS).count();
    }

    public int sortPrice() {
        products.sort((Products p1,Products p2)->p2.getItemPrice()-p1.getItemPrice());
        List<Products> bookProducts = products.stream().filter(p -> p.getCategory() == Category.valueOf("BOOKS")).collect(Collectors.toList());
        Double sum= Double.valueOf(products.stream().map(p->p.getItemPrice()).reduce(0,(a, b)->a+b));
        setTotalAmount(sum);
        return bookProducts.get(2).getItemPrice();
    }

}
