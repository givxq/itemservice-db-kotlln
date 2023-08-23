package com.example.itemservice

import com.example.itemservice.config.JdbcTemplateV1Config
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication(scanBasePackages = ["com.example.itemservice.web"])
@Import(JdbcTemplateV1Config::class)
class ItemServiceApplication

fun main(args: Array<String>) {
	runApplication<ItemServiceApplication>(*args)
}