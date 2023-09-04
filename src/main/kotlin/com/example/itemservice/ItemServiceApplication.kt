package com.example.itemservice

import com.example.itemservice.config.JdbcTemplateV1Config
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

val log = KotlinLogging.logger {}

@SpringBootApplication(scanBasePackages = ["com.example.itemservice.web"])
@Import(JdbcTemplateV1Config::class)
class ItemServiceApplication

fun main(args: Array<String>) {
	runApplication<ItemServiceApplication>(*args)
}