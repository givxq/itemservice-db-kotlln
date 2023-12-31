package com.example.itemservice.domain

import com.example.itemservice.log
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

val logger = KotlinLogging.logger { }

@SpringBootTest
@ActiveProfiles("test")
@Transactional
//@Rollback(value = false)
class ItemRepositoryTest(
    private val itemRepository: ItemRepository,
//    private val transactionManager: PlatformTransactionManager,
) : ShouldSpec({
    extension(SpringExtension)

    context("save/update") {
        should("save") {
            val item = Item(
                itemName = "itemA",
                price = 10000,
                quantity = 10
            )
            itemRepository.save(item)
            val findItem = itemRepository.findById(item.id!!).get()
            item shouldBe findItem
        }

        should("updateItem") {
            val item = Item(
                itemName = "item1",
                price = 10000,
                quantity = 10
            )
            val savedItem = itemRepository.save(item)
            val itemId = savedItem.id!!

            val updateParam = ItemUpdateDto("item2", 20000, 30)
            itemRepository.update(itemId, updateParam)

            val findItem = itemRepository.findById(itemId).get()
            findItem.itemName shouldBe updateParam.itemName
            findItem.price shouldBe updateParam.price
            findItem.quantity shouldBe updateParam.quantity
        }
    }

    context("find Items context") {
        val item1 = Item(
            itemName = "itemA-1",
            price = 10000,
            quantity = 10
        )
        val item2 = Item(
            itemName = "itemA-2",
            price = 20000,
            quantity = 20
        )
        val item3 = Item(
            itemName = "itemB-1",
            price = 30000,
            quantity = 30
        )

        itemRepository.save(item1)
        itemRepository.save(item2)
        itemRepository.save(item3)

        fun testItems(
            itemName: String?,
            maxPrice: Int?,
            vararg items: Item
        ) {
            val result = itemRepository.findAll(ItemSearchCond(itemName, maxPrice))
//            result.shouldContainExactly(items.toList())
            log.info { "+++ result : $result" }
            result shouldBe items
        }

        should("둘 다 없음 검증") {
            testItems(null, null, item1, item2, item3)
            testItems("", null, item1, item2, item3)
        }

        should("itemName 검증") {
            testItems("%itemA%", null, item1, item2)
            testItems("%temA%", null, item1, item2)
            testItems("%itemB%", null, item3)
        }

        should("maxPrice 검증") {
            testItems(null, 10000, item1)
        }

        should("둘 다 있음 검증") {
            testItems("%itemA%", 10000, item1)
        }
    }
})