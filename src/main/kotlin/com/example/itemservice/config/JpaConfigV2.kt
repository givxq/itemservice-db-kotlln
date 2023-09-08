package com.example.itemservice.config

import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.jpa.JpaItemRepositoryV2
import com.example.itemservice.repository.jpa.SpringDataJpaItemRepository
import com.example.itemservice.service.ItemServiceV1
import org.springframework.context.annotation.Bean

class JpaConfigV2(
    private val springDataJpaItemRepository: SpringDataJpaItemRepository
) {

    @Bean
    fun itemService(): ItemServiceV1 {
        return ItemServiceV1(itemRepositoty())
    }

    @Bean
    fun itemRepositoty(): ItemRepository {
        return JpaItemRepositoryV2(springDataJpaItemRepository)
    }

}