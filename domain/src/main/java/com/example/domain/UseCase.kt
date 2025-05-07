package com.example.domain

import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UseCase @Inject constructor(val repository: ItemRepository) {

    private fun changeCount(item: Item): Item {
        var changeItem = item
        if (changeItem.count!! > 0) {
            changeItem.count = changeItem.count?.minus(1)
        }
        var simpleDateFormat = SimpleDateFormat("ddMMyyyyHHmmss")
        var argDate: Long = simpleDateFormat.format(Date()).toLong()
        changeItem.date = argDate.toString().toLong()
        return changeItem
    }

    suspend fun itemPressedDone(item: Item) {
        repository.completeItem(item)
        changeCount(item)
        repository.updateItem(item)
        repository.editItem(item)
    }

    fun queryLocalItemsType(type: Int): Flow<List<Item>> {
        return repository.queryItemsTypefromDatabase(type)
    }

    suspend fun create(item: Item) {

        var keyFromDatabase = repository.insertItem(item)
        var jsonStr = repository.addItem(item)
        var itemUid: ItemUid = Json.decodeFromString<ItemUid>(jsonStr.first())
        var keyFromServer = itemUid.uid

        repository.updateItem(
            setUid(keyFromDatabase, keyFromServer, item)
        )

    }

    private fun setUid(id: Long, uid: String, item: Item): Item {
        var changeUidItem: Item = item
        changeUidItem.uid = uid
        changeUidItem.id = id.toInt()
        return changeUidItem
    }

    suspend fun update(item: Item) {
        repository.updateItem(item)
        repository.editItem(item)
    }

    suspend fun refreshItems() {
        if (repository.getCountRows().first() == 0) {
            repository.insertItems(repository.itemList.first())
        }
        else {
            repository.updateItems(repository.itemList.first())
        }
    }

}