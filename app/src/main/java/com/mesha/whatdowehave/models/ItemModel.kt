package com.mesha.whatdowehave.models

class ItemModel{

    var itemName: String = ""
    var quantity: Int = 0
    var expiration: String = ""

    constructor(itemName: String, quantity: Int, expiration: String){
        this.itemName = itemName
        this.quantity = quantity
        this.expiration = expiration
    }

}