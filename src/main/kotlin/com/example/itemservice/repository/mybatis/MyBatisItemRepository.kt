package com.example.itemservice.repository.mybatis

import com.example.itemservice.domain.Item
import com.example.itemservice.log
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import com.example.itemservice.repository.mybatis.mapper.ItemMapper
import java.util.*

class MyBatisItemRepository(
    private val itemMapper: ItemMapper
): ItemRepository {
    override fun save(item: Item): Item {
        log.info { "itemMapper class=${itemMapper.javaClass}" }
        itemMapper.save(item)
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        itemMapper.update(itemId, updateParam)
    }

    override fun findById(id: Long): Optional<Item> {
        return itemMapper.findById(id)
    }

    override fun findAll(cond: ItemSearchCond): List<Item> {
        return itemMapper.findAll(cond)
    }

    override fun deleteAll() {
    }
}