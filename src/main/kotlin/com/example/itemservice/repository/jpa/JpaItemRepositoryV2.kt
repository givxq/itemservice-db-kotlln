package com.example.itemservice.repository.jpa

import com.example.itemservice.domain.Item
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Transactional
class JpaItemRepositoryV2(
    private val repository: SpringDataJpaItemRepository
) : ItemRepository {

    override fun save(item: Item): Item {
        return repository.save(item)
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val findItem = repository.findById(itemId).orElseThrow()
        findItem.itemName = updateParam.itemName
        findItem.price = updateParam.price
        findItem.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Optional<Item> {
        return repository.findById(id)
    }

    override fun findAll(cond: ItemSearchCond): List<Item> {
        val (itemName, maxPrice) = cond

        return when {
            !itemName.isNullOrEmpty() && maxPrice != null -> repository.findByItemNameLikeAndPriceLessThanEqual(itemName, maxPrice)
            !itemName.isNullOrEmpty() -> repository.findByItemNameLike(itemName)
            maxPrice != null -> repository.findByPriceLessThanEqual(maxPrice)
            else -> repository.findAll()
        }
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}