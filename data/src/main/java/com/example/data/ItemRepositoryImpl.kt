package com.example.data

import androidx.lifecycle.MutableLiveData
import com.example.data.utils.ApiResult
import com.example.data.utils.HandlerApiResponses
import com.example.domain.HabitDone
import com.example.domain.Item
import com.example.domain.ItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*


class ItemRepositoryImpl(val itemDao: ItemDao, val itemApiService:ItemApiInterface, val dispatcher: CoroutineDispatcher) :
    ItemRepository {

    var uid: MutableLiveData<String> = MutableLiveData("")
    override var itemList: MutableLiveData<List<Item>> = MutableLiveData<List<Item>>()
    var errorMessage: MutableLiveData<String> = MutableLiveData("")

    override suspend fun deleteItems(){
        itemDao.deleteAll()
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work off the main thread.
    override suspend fun insertItems(items: List<com.example.domain.Item>) {
        for (item in items){
            val newItem: Item = Item(id = null, uid = item.uid,
                    title = item.title, description = item.description,
                    priority = item.priority, count = item.count,
                    type = item.type, frequency = item.frequency,
                    color = item.color, date = item.date, done_dates = item.done_dates // null
                )

            itemDao.insert(newItem)
        }
    }

    override fun queryItemsTypefromDatabase(type: Int): Flow<List<Item>> {
        return itemDao.getAllItemsType(type)
    }

    override suspend fun requestItems() {
        val response = HandlerApiResponses.safeApiCall (dispatcher) { itemApiService.getHabits() }
        when (response){
            is ApiResult.Success -> itemList = MutableLiveData(response.data)
            is ApiResult.Error -> errorMessage.postValue(response.message)
        }
    }

    override suspend fun addItem(item: Item) {
        val response = ( HandlerApiResponses.safeApiCall (dispatcher) { itemApiService.addHabit(item) })
        when (response){
            is ApiResult.Success -> uid = MutableLiveData(response.data)
            is ApiResult.Error -> errorMessage.postValue(response.message)
        }
    }

    override suspend fun editItem(item: Item) {
        val response =  HandlerApiResponses.safeApiCall (dispatcher) {itemApiService.updateHabit(item) }
        when (response){
            is ApiResult.Success -> uid = MutableLiveData(response.data)
            is ApiResult.Error -> errorMessage.postValue(response.message)
        }
    }

    override suspend fun completeItem(item: Item) {
        val newHabitDone: HabitDone = HabitDone(item.date!!, item.uid!!)
        val response =  HandlerApiResponses.safeApiCall (dispatcher) {itemApiService.completeHabit(newHabitDone) }
        when (response){
            is ApiResult.Success -> uid.value = response.data!!
            is ApiResult.Error -> errorMessage.value = response.message!!
        }
    }

}