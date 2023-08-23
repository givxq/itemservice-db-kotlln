package com.example.itemservice.domain

import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
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

    context("find Items context") {
        val item1 = Item("itemA-1", 10000, 10)
        val item2 = Item("itemA-2", 20000, 20)
        val item3 = Item("itemB-1", 30000, 30)

        itemRepository.save(item1)
        itemRepository.save(item2)
        itemRepository.save(item3)

        fun testItems(
            itemName: String?,
            maxPrice: Int?,
            vararg items: Item
        ) {
            val result = itemRepository.findAll(ItemSearchCond(itemName, maxPrice))
            result.shouldContainExactly(items.toList())
        }

        should("둘 다 없음 검증") {
            testItems(null, null, item1, item2, item3)
            testItems("", null, item1, item2, item3)
        }

        should("itemName 검증") {
            testItems("itemA", null, item1, item2)
            testItems("temA", null, item1, item2)
            testItems("itemB", null, item3)
        }

        should("maxPrice 검증") {
            testItems(null, 10000, item1)
        }

        should("둘 다 있음 검증") {
            testItems("itemA", 10000, item1)
        }
    }
})