package com.example.itemservice

import com.example.itemservice.config.MemoryConfig
import com.example.itemservice.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile

@SpringBootApplication(scanBasePackages = ["com.example.itemservice"])
@Import(MemoryConfig::class)
class ItemServiceApplication

fun main(args: Array<String>) {
	runApplication<ItemServiceApplication>(*args)
}