package com.example.itemservice.service

import com.example.itemservice.domain.Item
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import java.util.*

class ItemServiceV1(
    private val itemRepository: ItemRepository
) : ItemService {
    override fun save(item: Item): Item {
        return itemRepository.save(item)
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        itemRepository.update(itemId, updateParam)
    }

    override fun findById(id: Long): Optional<Item> {
        return itemRepository.findById(id)
    }

    override fun findItems(cond: ItemSearchCond): List<Item>? {
        return itemRepository.findAll(cond)
    }
}