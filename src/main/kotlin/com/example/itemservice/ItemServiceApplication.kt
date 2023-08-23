package com.example.itemservice

import com.example.itemservice.config.MemoryConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication(scanBasePackages = ["com.example.itemservice"])
@Import(MemoryConfig::class)
class ItemServiceApplication

fun main(args: Array<String>) {
	runApplication<ItemServiceApplication>(*args)
}