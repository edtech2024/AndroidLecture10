package com.example.myapplicationsixth.viewmodel

import com.example.domain.dataobject.Item
import com.example.domain.irepository.ItemRepository
import com.example.domain.usecase.IUseCase
import com.example.domain.usecase.UseCase
import com.example.myapplicationsixth.usecase.UseCaseImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.Mockito

class FakeUseCase: IUseCase {

    var dispatcher: CoroutineDispatcher =  UnconfinedTestDispatcher()

    var refreshIntervalMs: Long = 5000

    var items: MutableList<Item> = mutableListOf()

    override fun changeCount(item: Item): Item {
        TODO("Not yet implemented")
    }

    override suspend fun itemPressedDone(item: Item): Flow<String> {
        var fakeFlow: Flow<String> = flow {
            while(true) {
                var fakeString: String = "{\n" + "  \"uid\": \"12345\",\n" + "}\n"
                emit(fakeString)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
        return fakeFlow
    }

    override fun queryLocalItemsType(type: Int): Flow<List<Item>> {
        var fakeFlow: Flow<List<Item>> = flow {
            while(true) {
                var fakeItemList: List<Item> = listOf()
                emit(fakeItemList)
                kotlinx.coroutines.delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
        return fakeFlow
    }

    override suspend fun create(item: Item): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun getUid(jsonString: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun setUid(id: Long, uid: Flow<String>, item: Item): Item {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Item): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshItems() {
        var fakeFlow: Flow<List<Item>> = flow {
            while(true) {
                var fakeItemList: List<Item> = listOf()
                emit(fakeItemList)
                kotlinx.coroutines.delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }

}