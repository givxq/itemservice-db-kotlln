package com.example.itemservice.domain

import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemUpdateDto
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ItemRepositoryTest(
    @Autowired
    val itemRepository: ItemRepository
) : ShouldSpec({
    should("save") {
        val item = Item("itemA", 10000, 10)
        itemRepository.save(item)
        val findItem = itemRepository.findById(item.id).get()
        item shouldBe findItem
    }

    should("updateItem") {
        val item = Item("item1", 10000, 10)
        val savedItem = itemRepository.save(item)
        val itemId = savedItem.id

        val updateParam = ItemUpdateDto("item2", 20000, 30)
        itemRepository.update(itemId, updateParam)

        val findItem = itemRepository.findById(itemId).get()
        findItem.itemName shouldBe item.itemName
        findItem.price shouldBe item.price
        findItem.quantity shouldBe item.quantity
    }

    should("find Items") {
        val item1 = Item("itemA-1", 10000, 10)
        val item2 = Item("itemA-2", 20000, 20)
        val item3 = Item("itemB-1", 30000, 30)

        itemRepository.save(item1)
        itemRepository.save(item2)
        itemRepository.save(item3)


    }
})