package com.example.itemservice

import com.example.itemservice.domain.Item
import com.example.itemservice.repository.ItemRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

class TestDataInit(
    private val itemRepository: ItemRepository
) {
    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent::class)
    fun initData() {
        log.info { "init data" }
        itemRepository.save(
            Item(
                itemName = "itemA",
                price = 10000,
                quantity = 10
            )
        )
        itemRepository.save(
            Item(
                itemName = "itemB",
                price = 20000,
                quantity = 20
            )
        )
    }
}