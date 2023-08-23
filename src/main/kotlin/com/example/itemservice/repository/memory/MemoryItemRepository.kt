package com.example.itemservice.repository.memory

import com.example.itemservice.domain.Item
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import org.springframework.stereotype.Repository
import org.springframework.util.ObjectUtils
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors


@Repository
class MemoryItemRepository : ItemRepository {
    override fun save(item: Item): Item {
        item.id = ++sequence
        store[item.id!!] = item
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val findItem: Item = findById(itemId).orElseThrow()
        findItem.itemName = updateParam.itemName
        findItem.price = updateParam.price
        findItem.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Optional<Item> {
        return Optional.ofNullable<Item>(store[id])
    }

    override fun findAll(cond: ItemSearchCond): List<Item> {
        val itemName = cond.itemName
        val maxPrice = cond.maxPrice
        return store.values.stream()
            .filter(Predicate<Item> { item: Item ->
                if (ObjectUtils.isEmpty(itemName)) {
                    return@Predicate true
                }
                item.itemName!!.contains(itemName?:"")
            }).filter(Predicate<Item> { item: Item ->
                if (maxPrice == null || maxPrice == 0) {
                    return@Predicate true
                }
                item.price!! <= maxPrice
            })
            .collect(Collectors.toList<Item>())
    }

    fun clearStore() {
        store.clear()
    }

    companion object {
        private val store: MutableMap<Long, Item> = HashMap<Long, Item>() //static
        private var sequence = 0L //static
    }
}

