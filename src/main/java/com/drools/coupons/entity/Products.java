package com.drools.coupons.entity;


import com.drools.coupons.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "item_name",length = 100,nullable = false)
    private String itemName; //[book1,book2,laptop1,lpatop2]

    private Category category;   //Enum (Books, Electronics, Apparels, Gadgets)

    @Column(name = "sub_category",length = 100)
    private String subCategory;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @Column(name = "item_price",nullable = false)
    private Integer itemPrice;

}
