package com.example.itemservice.config

import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.jpa.JpaItemRepository
import com.example.itemservice.service.ItemService
import com.example.itemservice.service.ItemServiceV1
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JpaConfig(
    private val em: EntityManager
) {
    @Bean
    fun itemService(): ItemService {
        return ItemServiceV1(itemRepository())
    }

    @Bean
    fun itemRepository(): ItemRepository {
        return JpaItemRepository(em)
    }
}