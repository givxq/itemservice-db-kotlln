package com.example.itemservice

import com.example.itemservice.domain.Item
import com.example.itemservice.repository.ItemRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

val logger = KotlinLogging.logger {  }

@Profile("local")
@Component
class TestDataInit(
    private val itemRepository: ItemRepository
) {
    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent::class)
    fun initData() {
        logger.info { "init data" }
        itemRepository.save(Item("itemA", 10000, 10))
        itemRepository.save(Item("itemB", 20000, 20))
    }
}