package com.example.domain.usecase

import com.example.domain.dataobject.Item
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class) // JUnit 5
class UseCaseTest {

    /*@ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()*/

    var fakeItemList: Flow<List<Item>> = mock()
    var fakeItemsList: MutableList<Item> = mock()
    var fakeUid: Flow<String> = mock()
    var fakeErrorMessage: Flow<String> = mock()
    var fakeItem: Item = Item(1, "1",
        null, null,null,
        null, 1, null,
        null, null, null)
    var fakeInt: Int = 1
    var fakeID: Int = 1
    var fakeUID: String = "1"
    var fakeString: String = "1"

    @InjectMocks
    var fakeItemRepository: FakeItemRepository = spy()
    var useCase: UseCase = UseCase(fakeItemRepository)

    @BeforeEach // junit5
    fun setUp() {

    }

    @Test
    fun itemPressedDone() = runTest  {
        Mockito.doReturn(fakeString).`when`( fakeItemRepository).completeItem(fakeItem)
        Mockito.doReturn(fakeInt).`when`( fakeItemRepository).updateItem(fakeItem)
        Mockito.doReturn(fakeInt).`when`( fakeItemRepository).editItem(fakeItem)

        launch { useCase.itemPressedDone(fakeItem) }
        advanceUntilIdle()

        verify(fakeItemRepository).completeItem(fakeItem)
        verify(fakeItemRepository).updateItem(fakeItem)
        verify(fakeItemRepository).editItem(fakeItem)
    }

    @Test
    fun queryLocalItemsType() = runTest {
        Mockito.doReturn(fakeItemList).`when`( fakeItemRepository).queryItemsTypefromDatabase(fakeInt)
        launch { useCase.queryLocalItemsType(fakeInt) }
        advanceUntilIdle()
        verify(fakeItemRepository).queryItemsTypefromDatabase(fakeInt)
    }

    // исправить
    @Test
    fun create() = runTest {
        /*Mockito.doReturn(fakeInt.toLong()).`when`( fakeItemRepository).insertItem(fakeItem)
        Mockito.doReturn(fakeString).`when`( fakeItemRepository).addItem(fakeItem)
        Mockito.doReturn(fakeInt).`when`( fakeItemRepository).updateItem(fakeItem)*/

        launch { useCase.create(fakeItem) }
        advanceUntilIdle()

        verify(fakeItemRepository).insertItem(fakeItem)
        //verify(fakeItemRepository).addItem(fakeItem)
        //verify(fakeItemRepository).updateItem(fakeItem)
    }

    @Test
    fun update() = runTest {
        Mockito.doReturn(fakeID).`when`( fakeItemRepository).updateItem(fakeItem)
        Mockito.doReturn(fakeUID).`when`( fakeItemRepository).editItem(fakeItem)

        launch { useCase.update(fakeItem) }
        advanceUntilIdle()

        verify(fakeItemRepository).updateItem(fakeItem)
        verify(fakeItemRepository).editItem(fakeItem)
    }

    @Test
    suspend fun refreshItems() {
        /*
        fakeItemRepository.getCountRows().first()
        fakeItemRepository.insertItems(fakeItemsList)
        fakeItemRepository.updateItems(fakeItemsList)
        */
    }

}
