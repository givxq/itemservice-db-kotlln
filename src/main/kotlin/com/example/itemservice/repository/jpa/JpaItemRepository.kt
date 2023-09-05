package com.example.itemservice.repository.jpa

import com.example.itemservice.domain.Item
import com.example.itemservice.log
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
) : ItemRepository {
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
        var jpql = "select i from Item i"
        val maxPrice: Int? = cond.maxPrice
        val itemName: String? = cond.itemName
        if (!itemName.isNullOrEmpty() || maxPrice != null) {
            jpql += " where"
        }
        var andFlag = false
        if (!itemName.isNullOrEmpty()) {
            jpql += " i.itemName like concat('%',:itemName,'%')"
            andFlag = true
        }
        if (maxPrice != null) {
            if (andFlag) {
                jpql += " and"
            }
            jpql += " i.price <= :maxPrice"
        }
        log.info { "jpql=$jpql" }
        val query = em.createQuery(jpql, Item::class.java)
        if (!itemName.isNullOrEmpty()) {
            query.setParameter("itemName", itemName)
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice)
        }
        return query.resultList
    }

    override fun deleteAll() {

    }
}