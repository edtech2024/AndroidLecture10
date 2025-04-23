package com.example.myapplicationsixth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplicationsixth.Utils.ApiResult
import com.example.myapplicationsixth.Utils.HandlerApiResponses
import com.example.myapplicationsixth.domain.Item
import kotlinx.coroutines.CoroutineDispatcher


class ItemRepository(val itemDao: ItemDao, val itemApiService: ItemApiInterface, val dispatcher: CoroutineDispatcher) {

    var uid: MutableLiveData<String> = MutableLiveData("")
    lateinit var itemList: MutableLiveData<List<Item>>
    var errorMessage: MutableLiveData<String> = MutableLiveData("")

    suspend fun deleteItems(){
        itemDao.deleteAll()
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work off the main thread.
    suspend fun insertItems(items: List<Item>) {
        for (item in items){
            val newItem: Item = Item(id = null, uid = item.uid,
                title = item.title, description = item.description,
                priority = item.priority, count = item.count,
                type = item.type,  frequency = item.frequency,
                color = item.color, date = item.date )

            itemDao.insert(newItem)
        }
    }

    fun queryItemsTypefromDatabase(type: String): LiveData<List<Item>> {
        return itemDao.getAllItemsType(type)
    }

    suspend fun requestItems() {
        val response = HandlerApiResponses.safeApiCall (dispatcher) { itemApiService.getHabits() }
        when (response){
            is ApiResult.Success -> itemList = MutableLiveData(response.data)
            is ApiResult.Error -> errorMessage.postValue(response.message)
        }
    }

    suspend fun addItem(item: Item) {
        val response = ( HandlerApiResponses.safeApiCall (dispatcher) { itemApiService.addHabit(item) })
        when (response){
            is ApiResult.Success -> uid = MutableLiveData(response.data)
            is ApiResult.Error -> errorMessage.postValue(response.message)
        }
    }

    suspend fun editItem(item: Item) {
        val response =  HandlerApiResponses.safeApiCall (dispatcher) {itemApiService.updateHabit(item) }
        when (response){
            is ApiResult.Success -> uid = MutableLiveData(response.data)
            is ApiResult.Error -> errorMessage.postValue(response.message)
        }
    }

}