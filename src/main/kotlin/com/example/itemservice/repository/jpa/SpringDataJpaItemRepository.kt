package com.example.itemservice.repository.jpa

import com.example.itemservice.domain.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SpringDataJpaItemRepository : JpaRepository<Item, Long> {
    fun findByItemNameLike(itemName: String): List<Item>
    fun findByPriceLessThanEqual(price: Int): List<Item>
    fun findByItemNameLikeAndPriceLessThanEqual(itemName: String, price: Int): List<Item>

    @Query("select i from Item i where i.itemName like :itemName and i.price <= :price")
    fun findItems(@Param("itemName") itemName: String, @Param("price") price: Int): List<Item>
}