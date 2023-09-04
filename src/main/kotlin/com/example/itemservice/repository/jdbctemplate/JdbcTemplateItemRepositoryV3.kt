package com.example.itemservice.repository.jdbctemplate

import com.example.itemservice.domain.Item
import com.example.itemservice.log
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import java.util.*
import javax.sql.DataSource

class JdbcTemplateItemRepositoryV3(
    dataSource: DataSource
) : ItemRepository {

    private val template = NamedParameterJdbcTemplate(dataSource)
    private val jdbcInsert = SimpleJdbcInsert(dataSource)
        .withTableName("item")
        .usingGeneratedKeyColumns("id")

    override fun save(item: Item): Item {
        val param = BeanPropertySqlParameterSource(item)
        val key = jdbcInsert.executeAndReturnKey(param)
        item.id = key.toLong()
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val sql = "update item " +
                "set item_name=:itemName, price=:price, quantity=:quantity " +
                "where id=:id"
        val param = MapSqlParameterSource()
            .addValue("itemName", updateParam.itemName)
            .addValue("price", updateParam.price)
            .addValue("quantity", updateParam.quantity)
            .addValue("id", itemId)

        template.update(
            sql,
            param,
        )
    }

    override fun findById(id: Long): Optional<Item> {
        val sql = "select id, item_name, price, quantity from item where id = :id"
        val param: MutableMap<String, Any> = mutableMapOf("id" to id)
        val item = template.queryForObject(sql, param, itemRowMapper())
        return Optional.of(item!!)
    }

    private fun itemRowMapper(): RowMapper<Item> {
        return BeanPropertyRowMapper.newInstance(Item::class.java)
    }

    override fun findAll(cond: ItemSearchCond): List<Item>? {
        val itemName = cond.itemName
        val maxPrice = cond.maxPrice

        val param = BeanPropertySqlParameterSource(cond)

        var sql = "select id, item_name, price, quantity from item"

        if (!itemName.isNullOrEmpty() || maxPrice != null) {
            sql += " where"
        }

        var andFlag = false
        if (!itemName.isNullOrEmpty()) {
            sql += " item_name like concat('%',:itemName,'%')"
            andFlag = true
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and"
            }
            sql += " price <= :maxPrice"
        }

        log.info { "sql = $sql" }
        return template.query(sql, param, itemRowMapper())
    }

    override fun deleteAll() {
        val sql = "delete item"
        template.update(sql, mapOf<String, Any>())
    }
}