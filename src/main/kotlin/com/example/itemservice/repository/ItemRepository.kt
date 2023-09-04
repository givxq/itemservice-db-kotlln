package com.example.itemservice.repository

import com.example.itemservice.domain.Item
import java.util.*

interface ItemRepository {

    fun save(item: Item): Item

    fun update(itemId: Long, updateParam: ItemUpdateDto)

    fun findById(id: Long): Optional<Item>

    fun findAll(cond: ItemSearchCond): List<Item>?

    fun deleteAll()
}