package com.example.itemservice.domain

data class Item(
    var itemName: String = "",
    var price: Int = 0,
    var quantity: Int = 0,
) {
    var id: Long = 0
}

