package com.example.itemservice.domain

import jakarta.persistence.*

@Entity
data class Item(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "item_name", length = 10)
    var itemName: String? = null,

    var price: Int? = null,
    var quantity: Int? = null,
)
