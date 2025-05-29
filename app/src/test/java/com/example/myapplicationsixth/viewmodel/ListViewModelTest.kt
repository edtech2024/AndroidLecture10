package com.example.myapplicationsixth.viewmodel

import androidx.lifecycle.Observer
import com.example.domain.dataobject.Item
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito

@ExtendWith(InstantTaskExecutorExtension::class)
class ListViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @InjectMocks
    var fakeUseCase: FakeUseCase = Mockito.spy()
    var listViewModel: ListViewModel = ListViewModel(fakeUseCase)

    var fakeItem: Item = Item(1, "1",
        null, null,null,
        null, 1, null,
        null, null, null)
    var fakeString: String = "1"

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private val liveDataObserver: Observer<List<Item>> = mock()

    @BeforeEach
    fun setUp() {
        val testDispatcher = UnconfinedTestDispatcher(mainCoroutineRule.testScheduler)
        Dispatchers.setMain(testDispatcher)

        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()

        listViewModel = ListViewModel(fakeUseCase)
        listViewModel.itemsListType1.observe(lifeCycleTestOwner, liveDataObserver)
    }

    @Test
    fun itemDone() = runTest {
        Mockito.doReturn(fakeString).`when`(fakeUseCase).itemPressedDone(fakeItem)

        launch { listViewModel.itemDone(fakeItem) }
        advanceUntilIdle()

        Mockito.verify(fakeUseCase).itemPressedDone(fakeItem)
    }

    @AfterEach
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()

        Dispatchers.resetMain()
    }

}