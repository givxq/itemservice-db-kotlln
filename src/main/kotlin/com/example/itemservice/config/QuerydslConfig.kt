package com.example.itemservice.config

import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.jpa.JpaItemRepositoryV3
import com.example.itemservice.service.ItemServiceV1
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfig(
    private val em: EntityManager
) {

    @Bean
    fun itemService(): ItemServiceV1 {
        return ItemServiceV1(itemRepositoty())
    }

    @Bean
    fun itemRepositoty(): ItemRepository {
        return JpaItemRepositoryV3(em)
    }

}