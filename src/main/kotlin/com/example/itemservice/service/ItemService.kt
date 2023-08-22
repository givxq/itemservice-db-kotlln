package com.example.itemservice.service

import com.example.itemservice.domain.Item
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import java.util.*

interface ItemService {
    fun save(item: Item): Item

    fun update(itemId: Long, updateParam: ItemUpdateDto)

    fun findById(id: Long): Optional<Item>

    fun findItems(itemSearch: ItemSearchCond): List<Item>?
}