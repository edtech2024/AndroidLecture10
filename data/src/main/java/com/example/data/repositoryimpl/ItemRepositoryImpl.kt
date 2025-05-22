package com.example.data.repositoryimpl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.data.database.ItemDao
import com.example.data.utils.ApiResult
import com.example.data.utils.HandlerApiResponses
import com.example.data.web.ItemApiInterface
import com.example.domain.dataobject.HabitDone
import com.example.domain.dataobject.Item
import com.example.domain.irepository.ItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class ItemRepositoryImpl @Inject constructor(val itemDao: ItemDao,
                                             val itemApiService: ItemApiInterface,
                                             val dispatcher: CoroutineDispatcher,
                                             val refreshIntervalMs: Long
) : ItemRepository {

    var uid: Flow<String> = MutableLiveData("").asFlow().flowOn(dispatcher)
    var errorMessage: Flow<String> = MutableLiveData("").asFlow().flowOn(dispatcher)
    var itemList: Flow<List<Item>> = flow<List<Item>> {
        while(true) {
            val response = HandlerApiResponses.safeApiCall(dispatcher) { itemApiService.getHabits() }
            var items: List<Item> = listOf()
            when (response) {
                is ApiResult.Success -> items = response.data!!
                is ApiResult.Error -> errorMessage = MutableLiveData(response.message!!).asFlow()
            }
            emit(items)
            delay(refreshIntervalMs) // Suspends the coroutine for some time
        }
    }.flowOn(dispatcher)

    override suspend fun deleteItems() {
        itemDao.deleteAll()
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work off the main thread.
    override suspend fun insertItems(items: List<Item>) {
        for (item in items) {
            val newItem: Item = Item(id = null, uid = item.uid,
                title = item.title, description = item.description,
                priority = item.priority, count = item.count,
                type = item.type, frequency = item.frequency,
                color = item.color, date = item.date, done_dates = item.done_dates // null
            )

            itemDao.insert(newItem)
        }
    }

    override suspend fun insertItem(item: Item): Long {
        val newItem: Item = Item(id = null, uid = item.uid,
            title = item.title, description = item.description,
            priority = item.priority, count = item.count,
            type = item.type, frequency = item.frequency,
            color = item.color, date = item.date, done_dates = item.done_dates // null
        )

        return itemDao.insert(newItem)
    }

    override suspend fun updateItems(items: List<Item>) {
        for (item in items) {
            val newItem: Item = Item(id = item.id, uid = item.uid,
                title = item.title, description = item.description,
                priority = item.priority, count = item.count,
                type = item.type, frequency = item.frequency,
                color = item.color, date = item.date, done_dates = item.done_dates // null
            )

            itemDao.update(newItem)
        }
    }

    override suspend fun getCountRows(): Flow<Int> {
        return itemDao.getCount()
    }

    override suspend fun updateItem(item: Item): Flow<Int> {
        return MutableLiveData(itemDao.update(item)).asFlow()
    }

    override fun queryItemsTypefromDatabase(type: Int): Flow<List<Item>> {
        return itemDao.getAllItemsType(type)
    }

    override suspend fun requestItems() {
        val response = HandlerApiResponses.safeApiCall(dispatcher) { itemApiService.getHabits() }
        when (response) {
            is ApiResult.Success -> itemList = MutableLiveData(response.data!!).asFlow()
            is ApiResult.Error -> errorMessage = MutableLiveData(response.message!!).asFlow()
        }
    }

    override suspend fun addItem(item: Item): Flow<String> {
        val response = ( HandlerApiResponses.safeApiCall (dispatcher) { itemApiService.addHabit(item) })
        when (response){
            is ApiResult.Success -> return MutableLiveData(response.data!!).asFlow()
            is ApiResult.Error -> return MutableLiveData(response.message!!).asFlow()
        }
    }

    override suspend fun editItem(item: Item): Flow<String> {
        val response =  HandlerApiResponses.safeApiCall (dispatcher) {itemApiService.updateHabit(item) }
        when (response){
            is ApiResult.Success -> return MutableLiveData(response.data!!).asFlow()
            is ApiResult.Error -> return MutableLiveData(response.message!!).asFlow()
        }
    }

    override suspend fun completeItem(item: Item): Flow<String> {
        val newHabitDone: HabitDone = HabitDone(item.date!!, item.uid!!)
        val response =  HandlerApiResponses.safeApiCall (dispatcher) {itemApiService.completeHabit(newHabitDone) }
        when (response){
            is ApiResult.Success -> return MutableLiveData(response.data!!).asFlow()
            is ApiResult.Error -> return MutableLiveData(response.message!!).asFlow()
        }
    }

}