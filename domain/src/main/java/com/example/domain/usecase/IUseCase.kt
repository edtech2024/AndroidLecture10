package com.example.domain.usecase

import com.example.domain.dataobject.Item
import com.example.domain.irepository.ItemRepository
import kotlinx.coroutines.flow.Flow


interface IUseCase {

    fun changeCount(item: Item): Item

    suspend fun itemPressedDone(item: Item): Flow<String>

    fun queryLocalItemsType(type: Int): Flow<List<Item>>

    suspend fun create(item: Item): Flow<Int>

    fun getUid(jsonString: String): String

    suspend fun setUid(id: Long, uid: Flow<String>, item: Item): Item

    suspend fun update(item: Item): Flow<String>

    suspend fun refreshItems()

}