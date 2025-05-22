package com.example.myapplicationsixth.usecase

import com.example.domain.dataobject.Item
import com.example.domain.dataobject.ItemUid
import com.example.domain.irepository.ItemRepository
import com.example.domain.usecase.IUseCase
import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UseCaseImpl @Inject constructor(val repository: ItemRepository) : IUseCase {

    override fun changeCount(item: Item): Item {
        var changeItem = item
        if (changeItem.count!! > 0) {
            changeItem.count = changeItem.count?.minus(1)
        }
        var simpleDateFormat = SimpleDateFormat("ddMMyyyyHHmmss")
        var argDate: Long = simpleDateFormat.format(Date()).toLong()
        changeItem.date = argDate.toString().toLong()
        return changeItem
    }

    override suspend fun itemPressedDone(item: Item): Flow<String> {
        repository.completeItem(item)
        changeCount(item)
        repository.updateItem(item)
        return repository.editItem(item)
    }

    override fun queryLocalItemsType(type: Int): Flow<List<Item>> {
        return repository.queryItemsTypefromDatabase(type)
    }

    override suspend fun create(item: Item): Flow<Int>  {
        /*var keyFromDatabase = repository.insertItem(item)
        //var jsonStr = repository.addItem(item)

        /*var itemUid: ItemUid = Json.decodeFromString<ItemUid>(jsonStr.first())
        var keyFromServer = itemUid.uid*/

        //var keyFromServer = getUid(jsonStr.first())
        var keyFromServer = (repository.addItem(item).first())

        return repository.updateItem(
            setUid(keyFromDatabase, keyFromServer, item)
        )*/

        return repository.updateItem(
            setUid(
                repository.insertItem(item),
                repository.addItem(item),
                item)
        )
    }

    override fun getUid(jsonString: String): String {
        var itemUid: ItemUid = Json.decodeFromString<ItemUid>(jsonString)
        var uidFromServer = itemUid.uid
        return uidFromServer
    }

    override suspend fun setUid(id: Long, uid: Flow<String>, item: Item): Item {
        var changeUidItem: Item = item
        //changeUidItem.uid =
        //uid.firstOrNull()?.let{ changeUidItem.uid = it } ?: "default"
        //uid.collect{ it?.let{ changeUidItem.uid = it } }
        uid.collect { a ->
            if (a.isNullOrEmpty()) {
                changeUidItem.uid = "default"
            } else {
                changeUidItem.uid = a
            }
        }
        changeUidItem.id = id.toInt()
        return changeUidItem
    }

    // ???
    override suspend fun update(item: Item): Flow<String> {
        repository.updateItem(item)
        return repository.editItem(item)
    }

    override suspend fun refreshItems() {
        /*
            if (repository.getCountRows().first() == 0) {
                repository.insertItems(repository.itemList.first())
            }
            else {
                repository.updateItems(repository.itemList.first())
            }
            */
    }

}