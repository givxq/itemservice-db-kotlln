package com.example.itemservice.repository.jpa

import com.example.itemservice.domain.Item
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class JpaItemRepository(
    private val em: EntityManager
): ItemRepository {
    override fun save(item: Item): Item {
        em.persist(em)
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<Item> {
        return Optional.of(em.find(Item::class.java, id))
    }

    override fun findAll(cond: ItemSearchCond): List<Item>? {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}