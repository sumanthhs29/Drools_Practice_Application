package com.drools.coupons.entity;

import com.drools.coupons.enums.DeliveryPreference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "shipping_details")
public class ShippingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    @Column(name = "shipping_address",nullable = false)
    private String shippingAddress;

    @Column(name = "delivery_preference")
    private DeliveryPreference deliveryPreference= DeliveryPreference.BEST_EFFORT;


}
