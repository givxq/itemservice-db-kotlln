package com.example.itemservice.config

import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.jdbctemplate.JdbcTemplateItemRepositoryV3
import com.example.itemservice.service.ItemService
import com.example.itemservice.service.ItemServiceV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbcTemplateV3Config(
    private val dataSource: DataSource
) {
    @Bean
    fun itemService(): ItemService {
        return ItemServiceV1(itemRepository())
    }

    @Bean
    fun itemRepository(): ItemRepository {
        return JdbcTemplateItemRepositoryV3(dataSource)
    }
}