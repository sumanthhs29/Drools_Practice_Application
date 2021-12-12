# Drools_Practice_Application

Connect to Postgres SQL database / Or comment the database code.

1] checkout order -> POST /checkout
{
    "products":[
        {
            "id":1,
            "itemName":"Marvel",
            "category":0,
            "subCategory":"Comic",
            "quantity":1,
            "itemPrice":400
        },
        {
            "id":2,
            "itemName":"Lion King",
            "category":0,
            "subCategory":"Comic",
            "quantity":1,
            "itemPrice":500
        },
        {
            "id":3,
            "itemName":"Alladin",
            "category":0,
            "subCategory":"Comic",
            "quantity":1,
            "itemPrice":600
        },
        {
            "id":4,
            "itemName":"EliteBook",
            "category":1,
            "subCategory":"Laptop",
            "quantity":1,
            "itemPrice":350
        },
        {
            "id":5,
            "itemName":"He-Man",
            "category":0,
            "subCategory":"Comic",
            "quantity":1,
            "itemPrice":300
        }
    ],
    "shippingDetails":{
        "id":1,
        "customer":{
            "id":1,
            "name":"Sumanth",
            "type":"gold",
            "availedCoupons":["20POFF","BUY2GET50POFF"],
            "spend":0
        },
        "shippingAddress":"Tumkur",
        "deliveryPreference":1
    },
    "coupons":[
        {
            "id":1,
            "couponName":"NEW50OFF",
            "startDate":"2021-02-12",
            "endDate":"2022-02-12",
            "amount":50
        },
        {
            "id":2,
            "couponName":"20POFF",
            "startDate":"2020-02-12",
            "endDate":"2022-02-12",
            "value":20
        },
        {
            "id":3,
            "couponName":"BUY2GET50POFF",
            "startDate":"2020-02-12",
            "endDate":"2022-02-12",
            "value":50
        }
    ],
    "customer":{
        "id":1,
        "name":"Sumanth",
        "type":"gold",
        "availedCoupons":["BUY2GET50POFF","20POFF"],
        "spend":0
    },
    "totalAmount":600
}

2] for spread sheet -> GET /testSpreadSheet
same body
