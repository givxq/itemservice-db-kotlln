package com.example.itemservice

import com.example.itemservice.config.JpaConfigV2
import com.example.itemservice.repository.ItemRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource


val log = KotlinLogging.logger {}

@SpringBootApplication(scanBasePackages = ["com.example.itemservice.web"])
//@Import(JdbcTemplateV3Config::class)
//@Import(MyBatisConfig::class)
//@Import(JpaConfig::class)
@Import(JpaConfigV2::class)
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

@Bean
@Profile("test")
fun dataSource(): DataSource {
	log.info { "메모리 데이터베이스 초기화" }
	val dataSource = DriverManagerDataSource()
	dataSource.setDriverClassName("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1")
	dataSource.username = "sa"
	dataSource.password = ""
	return dataSource
}