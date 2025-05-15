package com.example.domain.irepository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.domain.dataobject.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow


interface ItemRepository {

    var itemList: Flow<List<Item>>

    var uid: Flow<String>

    var errorMessage: Flow<String>

    suspend fun deleteItems()

    suspend fun insertItems(items: List<Item>)

    suspend fun insertItem(item: Item): Long

    suspend fun updateItem(item: Item)

    suspend fun updateItems(items: List<Item>)

    suspend fun getCountRows(): Flow<Int>

    fun queryItemsTypefromDatabase(type: Int): Flow<List<Item>>

    suspend fun requestItems()

    suspend fun addItem(item: Item): Flow<String>

    suspend fun editItem(item: Item)

    suspend fun completeItem(item: Item)

}