package com.example.domain

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow


interface ItemRepository {

    var itemList: MutableLiveData<List<Item>>

    suspend fun deleteItems()

    suspend fun insertItems(items: List<Item>)

    fun queryItemsTypefromDatabase(type: Int): Flow<List<Item>>

    suspend fun requestItems()

    suspend fun addItem(item: Item)

    suspend fun editItem(item: Item)

    suspend fun completeItem(item: Item)

}