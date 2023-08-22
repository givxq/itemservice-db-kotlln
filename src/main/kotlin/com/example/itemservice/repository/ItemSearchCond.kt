package com.example.itemservice.repository

data class ItemSearchCond(
    var itemName: String = "",
    var maxPrice: Int = 0,
)