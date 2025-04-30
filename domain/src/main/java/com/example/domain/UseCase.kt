package com.example.domain

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UseCase @Inject constructor(val repository: ItemRepository) {

    suspend fun changeCount(item: Item): Item {
        var changeItem = item
        if (changeItem.count!! > 0) {
            changeItem.count = changeItem.count?.minus(1)
        }
        var simpleDateFormat = SimpleDateFormat("ddMMyyyyHHmmss")
        var argDate: Long = simpleDateFormat.format(Date()).toLong()
        changeItem.date = argDate.toString().toLong()
        return changeItem
    }

    suspend fun itemPressed(item: Item) {
        repository.completeItem(item)
        repository.editItem(changeCount(item))
        repository.requestItems()
        repository.deleteItems()
        repository.insertItems(repository.itemList.value!!)
    }

    fun queryLocalItemsType(type: Int): Flow<List<Item>> {
        return repository.queryItemsTypefromDatabase(type)
    }

    suspend fun create(item: Item){
        repository.addItem(item)
        repository.requestItems()
        repository.deleteItems()
        repository.itemList.value?.let { repository.insertItems(it) }
    }

    suspend fun update(item: Item){
        repository.editItem(item)
        repository.requestItems()
        repository.deleteItems()
        repository.itemList.value?.let { repository.insertItems(it) }
    }

    suspend fun requestItems(){
        repository.requestItems()
        repository.deleteItems()
        repository.itemList.value?.let { repository.insertItems(it) }
    }

}