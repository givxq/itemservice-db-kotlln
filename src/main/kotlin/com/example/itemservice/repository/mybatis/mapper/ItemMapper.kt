package com.example.itemservice.repository.mybatis.mapper

import com.example.itemservice.domain.Item
import com.example.itemservice.repository.ItemSearchCond
import com.example.itemservice.repository.ItemUpdateDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import java.util.*

@Mapper
interface ItemMapper {
    fun save(item: Item)

    fun update(@Param("id") id: Long, @Param("updateParam") updateParam: ItemUpdateDto)

    fun findAll(itemSearch: ItemSearchCond): List<Item>

    fun findById(id: Long): Optional<Item>
}
