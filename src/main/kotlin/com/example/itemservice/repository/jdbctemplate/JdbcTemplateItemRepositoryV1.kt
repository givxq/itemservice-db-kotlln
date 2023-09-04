package com.example.itemservice.repository.jdbctemplate

import com.example.itemservice.domain.Item
import com.example.itemservice.log
import com.example.itemservice.repository.ItemRepository
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*
import javax.sql.DataSource

class JdbcTemplateItemRepositoryV1(
    dataSource: DataSource
) : ItemRepository {

    private val template = JdbcTemplate(dataSource)
    override fun save(item: Item): Item {
        val sql = "insert into item(item_name, price, quantity) values (?,?,?)"
        val keyHolder = GeneratedKeyHolder()
        template.update({
            val ps: PreparedStatement = it.prepareStatement(sql, arrayOf("id"))
            ps.setString(1, item.itemName)
            ps.setInt(2, item.price!!)
            ps.setInt(3, item.quantity!!)
            ps
        }, keyHolder)

        val key = keyHolder.key!!.toLong()
        item.id = key

        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val sql = "update item set item_name=?, price=?, quantity=? where id=?"
        template.update(
            sql,
            updateParam.itemName,
            updateParam.price,
            updateParam.quantity,
            itemId
        )
    }

    override fun findById(id: Long): Optional<Item> {
        val sql = "select id, item_name, price, quantity from item where id = ?"
        val item = template.queryForObject(sql, itemRowMapper(), *arrayOf(id))
        return Optional.of(item!!)
    }

    private fun itemRowMapper(): RowMapper<Item> {
        return RowMapper { rs: ResultSet, _: Int ->
            Item(
                rs.getLong("id"),
                rs.getString("item_name"),
                rs.getInt("price"),
                rs.getInt("quantity")
            )
        }
    }

    override fun findAll(cond: ItemSearchCond): List<Item>? {
        val itemName = cond.itemName
        val maxPrice = cond.maxPrice

        var sql = "select id, item_name, price, quantity from item"

        if (!itemName.isNullOrEmpty() || maxPrice != null) {
            sql += " where"
        }

        var andFlag = false
        val param: MutableList<Any> = mutableListOf()
        if (!itemName.isNullOrEmpty()) {
            sql += " item_name like concat('%',?,'%')"
            param.add(itemName)
            andFlag = true
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and"
            }
            sql += " price <= ?"
            param.add(maxPrice)
        }

        log.info { "sql = $sql" }
        return template.query(sql, itemRowMapper(), *param.toTypedArray())
    }

    override fun deleteAll() {
        val sql = "delete item"
        template.update(sql)
    }
}