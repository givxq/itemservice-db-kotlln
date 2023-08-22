package com.example.itemservice.repository

data class ItemUpdateDto(
    var itemName: String,
    var price: Int,
    var quantity: Int,
)