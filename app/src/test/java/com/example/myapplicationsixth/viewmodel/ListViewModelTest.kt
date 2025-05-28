package com.example.myapplicationsixth.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.domain.dataobject.Item
import com.example.domain.usecase.UseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito

@ExtendWith(InstantTaskExecutorExtension::class)
class ListViewModelTest {

    /*@get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()*/

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @InjectMocks
    var fakeUseCase: FakeUseCase = Mockito.spy()
    var listViewModel: ListViewModel = ListViewModel(fakeUseCase)

    var argSearch: String = ""

    private var mediatorType1: MediatorLiveData<List<Item>> = MediatorLiveData<List<Item>>()
    private var mediatorType2: MediatorLiveData<List<Item>> = MediatorLiveData<List<Item>>()

    private var liveDataType1: LiveData<List<Item>> = fakeUseCase.queryLocalItemsType(0).asLiveData()
    private var liveDataType2: LiveData<List<Item>> = fakeUseCase.queryLocalItemsType(1).asLiveData()

    var tempListType1: MutableList<Item> = mutableListOf()
    var tempListType2: MutableList<Item> = mutableListOf()

    var itemsListType1: LiveData<List<Item>> = mediatorType1
    var itemsListType2: LiveData<List<Item>> = mediatorType2

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

    @Test
    fun getArgSearch() {
    }

    @Test
    fun setArgSearch() {
    }

    @Test
    fun getTempListType1() {
    }

    @Test
    fun setTempListType1() {
    }

    @Test
    fun getTempListType2() {
    }

    @Test
    fun setTempListType2() {
    }

    @Test
    fun getItemsListType1() {
    }

    @Test
    fun setItemsListType1() {
    }

    @Test
    fun getItemsListType2() {
    }

    @Test
    fun setItemsListType2() {
    }

    @Test
    fun loading() {
    }

    @Test
    fun applySortMethod() = runTest {

        launch { listViewModel.applySortMethod() }
        advanceUntilIdle()

        verify(listViewModel).sortByCount()
    }

    @Test
    fun applyFilterMethod() {
    }

    @Test
    fun applyResetFilter() {
    }

    @Test
    fun sortById() {
    }

    @Test
    fun sortByCount() {
    }

    @Test
    fun filtrationByTitle() {
    }

    @Test
    fun itemDone() = runTest {
        Mockito.doReturn(fakeString).`when`(fakeUseCase).itemPressedDone(fakeItem)

        launch { listViewModel.itemDone(fakeItem) }
        advanceUntilIdle()

        Mockito.verify(fakeUseCase).itemPressedDone(fakeItem)
    }

    @Test
    fun getUseCase() {
    }

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private val liveDataObserver: Observer<List<Item>> = mock()

    @BeforeEach
    fun setUp() {

        /*val testDispatcher = StandardTestDispatcher(mainCoroutineRule.testScheduler)
        val testScope = TestScope(testDispatcher)*/

        val testDispatcher = UnconfinedTestDispatcher(mainCoroutineRule.testScheduler)
        Dispatchers.setMain(testDispatcher)

        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()

        listViewModel = ListViewModel(fakeUseCase)
        listViewModel.itemsListType1.observe(lifeCycleTestOwner, liveDataObserver)

    }

    @AfterEach
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()

        Dispatchers.resetMain()
    }

}