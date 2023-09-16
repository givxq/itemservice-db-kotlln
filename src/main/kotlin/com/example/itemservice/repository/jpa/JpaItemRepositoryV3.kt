package com.example.itemservice.repository.jpa

import com.example.itemservice.domain.Item
import com.example.itemservice.domain.QItem.item
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Transactional
class JpaItemRepositoryV3(
    private val em: EntityManager
): ItemRepository {
    private val query = JPAQueryFactory(em)
    override fun save(item: Item): Item {
        em.persist(item)
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val item = em.find(Item::class.java, itemId)
        item.price = updateParam.price
        item.itemName = updateParam.itemName
        item.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Optional<Item> {
        val item = em.find(Item::class.java, id)
        return Optional.ofNullable(item)
    }

    override fun findAll(cond: ItemSearchCond): List<Item>? {
        val (itemName, maxPrice) = cond

        val builder = BooleanBuilder()

        if(!itemName.isNullOrEmpty())
            builder.and(item.itemName.like("%$itemName%"))

        if(maxPrice != null)
            builder.and(item.price.loe(maxPrice))

        return query.select(item)
            .from(item)
            .where(builder)
            .fetch()
    }

    private fun likeItemName(itemName: String?): BooleanExpression? {
        if(!itemName.isNullOrEmpty())
            return item.itemName.like("%$itemName%")
        return null
    }


    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}