package com.example.domain.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.domain.dataobject.Item
import com.example.domain.irepository.ItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
//import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock


class FakeItemRepository(
    /*override var itemList: Flow<List<Item>>,
    override var uid: Flow<String>,
    override var errorMessage: Flow<String>*/
) : ItemRepository {
    /*
    companion object {
             operator fun invoke(
                 itemList: Flow<List<Item>>,
                 uid: Flow<String>,
                 errorMessage: Flow<String>
             ) = FakeItemRepository (
                 itemList = itemList ?: MutableLiveData<List<Item>>().asFlow(),
                 uid = uid ?: MutableLiveData<String>().asFlow(),
                 errorMessage = errorMessage  ?: MutableLiveData<String>().asFlow()
             )
     }
     */

    var dispatcher: CoroutineDispatcher = mock()

    var refreshIntervalMs: Long = 5000

    var items: MutableList<Item> = mutableListOf()

    override suspend fun deleteItems() {
        TODO("Not yet implemented")
    }

    override suspend fun insertItems(items: List<Item>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertItem(item: Item): Long {
        //val items: MutableList<Item> = mutableListOf()
        items += item

        //var fakeDataLong: Long = 1
        return items.size.toLong()
    }

    override suspend fun updateItem(item: Item): Flow<Int>  {
        //items[item.id!!] = item
        //return item.id!!
        var fakeFlow: Flow<Int> = flow {
            while(true) {
                //var fakeItemList: List<Item> = listOf()

                items[item.id!!] = item
                item.id!!

                emit(item.id!!)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
        return fakeFlow
    }

    override suspend fun updateItems(items: List<Item>) {
        TODO("Not yet implemented")
    }

    override suspend fun getCountRows(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun queryItemsTypefromDatabase(type: Int): Flow<List<Item>> {
        var fakeFlow: Flow<List<Item>> = flow {
            while(true) {
                var fakeItemList: List<Item> = listOf()
                emit(fakeItemList)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
        return fakeFlow
    }

    override suspend fun requestItems() {
        TODO("Not yet implemented")
    }

    override suspend fun addItem(item: Item): Flow<String> {
        var fakeFlow: Flow<String> = flow {
            while(true) {
                var fakeString: String = "{\n" + "  \"uid\": \"12345\",\n" + "}\n"
                emit(fakeString)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
        return fakeFlow
    }

    override suspend fun editItem(item: Item): Flow<String> {
        var fakeFlow: Flow<String> = flow {
            while(true) {
                var fakeString: String = ""
                emit(fakeString)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
        return fakeFlow
    }

    // ???
    override suspend fun completeItem(item: Item): Flow<String> {
        var fakeFlow: Flow<String> = flow {
            while(true) {
                var fakeString: String = ""
                emit(fakeString)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
        return fakeFlow
    }
}