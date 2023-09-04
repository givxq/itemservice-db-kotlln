package com.example.itemservice

import com.example.itemservice.config.JdbcTemplateV3Config
import com.example.itemservice.repository.ItemRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile


val log = KotlinLogging.logger {}

@SpringBootApplication(scanBasePackages = ["com.example.itemservice.web"])
@Import(JdbcTemplateV3Config::class)
class ItemServiceApplication

fun main(args: Array<String>) {
	runApplication<ItemServiceApplication>(*args)
}

@Bean
@Profile("local")
fun testDataInit(itemRepository: ItemRepository?): TestDataInit {
	log.info { "init!!" }
	return TestDataInit(itemRepository!!)
}