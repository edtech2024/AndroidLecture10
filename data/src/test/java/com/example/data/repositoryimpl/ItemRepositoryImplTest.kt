package com.example.data.repositoryimpl

import com.example.data.database.ItemDao
import com.example.data.web.ItemApiInterface
import com.example.domain.dataobject.HabitDone
import com.example.domain.dataobject.Item
import com.example.domain.usecase.UseCase
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito

class ItemRepositoryImplTest {

    var fakeItemInsert: Item = Item(null,null,
        null, null,null,
        null, 1, null,
        null, null, null)

    var fakeItemUpdate: Item = Item(1, "1",
        null, null,null,
        null, 1, null,
        null, 2025, null)

    var fakeInt: Int = 1
    var fakeID: Int = 1
    var fakeUID: String = "1"
    var fakeString: String = "1"
    var fakeItemList: Flow<List<Item>> = mock()
    var fakeHabitDone: HabitDone = HabitDone(fakeItemUpdate.date!!, fakeUID)

    var itemDao: ItemDao = Mockito.mock()
    var itemApiService: ItemApiInterface = Mockito.mock()
    //var dispatcher = StandardTestDispatcher()
    var dispatcher = UnconfinedTestDispatcher()

    var refreshIntervalMs: Long = 500

    var itemRepositoryImpl: ItemRepositoryImpl = ItemRepositoryImpl(itemDao, itemApiService, dispatcher, refreshIntervalMs)

    /*
    @Test
    fun getUid() {
    }
    @Test
    fun setUid() {
    }

    @Test
    fun getErrorMessage() {
    }
    @Test
    fun setErrorMessage() {
    }

    @Test
    fun getItemList() {
    }
    @Test
    fun setItemList() {
    }

    @Test
    fun getItemDao() {
    }
    @Test
    fun getItemApiService() {
    }
    @Test
    fun getDispatcher() {
    }
    @Test
    fun getRefreshIntervalMs() {
    }
    */

    @Test
    fun deleteItems() {
    }
    @Test
    fun insertItems() {
    }
    @Test
    fun updateItems() {
    }
    @Test
    fun getCountRows() {
    }

    @Test
    fun insertItem() = runTest {
        Mockito.doReturn(fakeID).`when`(itemDao).insert(fakeItemInsert)

        launch { itemRepositoryImpl.insertItem(fakeItemInsert) }
        advanceUntilIdle()

        Mockito.verify(itemDao).insert(fakeItemInsert)
    }

    @Test
    fun updateItem() = runTest {
        Mockito.doReturn(fakeID).`when`(itemDao).update(fakeItemUpdate)

        launch { itemRepositoryImpl.updateItem(fakeItemUpdate) }
        advanceUntilIdle()

        Mockito.verify(itemDao).update(fakeItemUpdate)
    }

    @Test
    fun queryItemsTypefromDatabase() = runTest {
        Mockito.doReturn(fakeItemList).`when`(itemDao).getAllItemsType(fakeInt)

        launch { itemRepositoryImpl.queryItemsTypefromDatabase(fakeInt) }
        advanceUntilIdle()

        Mockito.verify(itemDao).getAllItemsType(fakeInt)
    }

    @Test
    fun requestItems() = runTest {
        Mockito.doReturn(fakeItemList).`when`(itemApiService).getHabits()

        launch { itemRepositoryImpl.requestItems() }
        advanceUntilIdle()

        Mockito.verify(itemApiService).getHabits()
    }

    @Test
    fun addItem() = runTest {
        Mockito.doReturn(fakeUID).`when`(itemApiService).addHabit(fakeItemInsert)

        launch { itemRepositoryImpl.addItem(fakeItemInsert) }
        advanceUntilIdle()

        Mockito.verify(itemApiService).addHabit(fakeItemInsert)
    }

    @Test
    fun editItem() = runTest {
        Mockito.doReturn(fakeUID).`when`(itemApiService).updateHabit(fakeItemUpdate)

        launch { itemRepositoryImpl.editItem(fakeItemUpdate) }
        advanceUntilIdle()

        Mockito.verify(itemApiService).updateHabit(fakeItemUpdate)
    }

    @Test
    fun completeItem() = runTest  {
        Mockito.doReturn(fakeString).`when`(itemApiService).completeHabit(fakeHabitDone)

        launch { itemRepositoryImpl.completeItem(fakeItemUpdate) }
        advanceUntilIdle()

        Mockito.verify(itemApiService).completeHabit(fakeHabitDone)
    }

}