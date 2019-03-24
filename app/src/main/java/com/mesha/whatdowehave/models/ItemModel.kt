package com.mesha.whatdowehave.models

class ItemModel{

    var itemId: Int = 0
    var itemName: String = ""
    var quantity: Int = 0
    var expiration: String = ""

    constructor(itemId: Int, itemName: String, quantity: Int, expiration: String){
        this.itemId = itemId
        this.itemName = itemName
        this.quantity = quantity
        this.expiration = expiration
    }

}