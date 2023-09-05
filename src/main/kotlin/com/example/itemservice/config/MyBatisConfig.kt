package com.example.itemservice.config

import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.mybatis.MyBatisItemRepository
import com.example.itemservice.repository.mybatis.mapper.ItemMapper
import com.example.itemservice.service.ItemService
import com.example.itemservice.service.ItemServiceV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyBatisConfig(
    private val itemMapper: ItemMapper
) {
    @Bean
    fun itemService(): ItemService {
        return ItemServiceV1(itemRepository())
    }

    @Bean
    fun itemRepository(): ItemRepository {
        return MyBatisItemRepository(itemMapper)
    }
}