package com.example.logisticshowcase.data.firebase

enum class FirestoreCollection(var value: String){
    USERS("user"),
    ORDERS("orders"),
    ITEM_ORDER("item_order"),
    CUSTOMERS("customers"),
}