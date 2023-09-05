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
        val findItem = em.find(Item::class.java, itemId)
        findItem.itemName = updateParam.itemName
        findItem.price = updateParam.price
        findItem.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Optional<Item> {
        return Optional.of(em.find(Item::class.java, id))
    }

    override fun findAll(cond: ItemSearchCond): List<Item>? {
        val jpql = "select i from Item i"
        return em.createQuery(jpql, Item::class.java)
            .resultList
    }

    override fun deleteAll() {

    }
}