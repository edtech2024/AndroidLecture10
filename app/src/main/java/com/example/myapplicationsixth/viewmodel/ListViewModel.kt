package com.example.myapplicationsixth.viewmodel

import androidx.lifecycle.*
import com.example.domain.dataobject.Item
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ListViewModel @Inject constructor(
    val useCase: UseCase
) : ViewModel() {

    var argSearch: String = ""

    private var mediatorType1: MediatorLiveData<List<Item>> = MediatorLiveData<List<Item>>()
    private var mediatorType2: MediatorLiveData<List<Item>> = MediatorLiveData<List<Item>>()

    private var liveDataType1: LiveData<List<Item>> = useCase.queryLocalItemsType(0).asLiveData()
    private var liveDataType2: LiveData<List<Item>> = useCase.queryLocalItemsType(1).asLiveData()

    var tempListType1: MutableList<Item> = mutableListOf()
    var tempListType2: MutableList<Item> = mutableListOf()

    var itemsListType1: LiveData<List<Item>> = mediatorType1
    var itemsListType2: LiveData<List<Item>> = mediatorType2

    init {

        viewModelScope.launch(Dispatchers.IO) {
            withContext(NonCancellable) {
                useCase.refreshItems()
            }
        }

        mediatorType1.addSource(liveDataType1) { source ->
            tempListType1.addAll(source)
            mediatorType1.setValue(tempListType1)
        }

        mediatorType2.addSource(liveDataType2) { source ->
            tempListType2.addAll(source)
            mediatorType2.setValue(tempListType2)
        }

    }

    fun applySortMethod() {
        sortByCount()
    }

    fun applyFilterMethod() {
        filtrationByTitle(argSearch)
    }

    fun applyResetFilter() {
        argSearch = ""
        sortById()

        mediatorType1.postValue(tempListType1)
        mediatorType2.postValue(tempListType2)
    }

    fun sortById(){
        tempListType1.sortBy { item -> item.id }
        mediatorType1.postValue(tempListType1)

        tempListType2.sortBy { item -> item.id }
        mediatorType2.postValue(tempListType2)
    }

    fun sortByCount() {
        tempListType1.sortBy { item -> item.count }
        mediatorType1.postValue(tempListType1)

        tempListType2.sortBy { item -> item.count }
        mediatorType2.postValue(tempListType2)
    }

    fun filtrationByTitle(title: String){
        var filteredListT1 = tempListType1.filter { it.title!!.contains(title, ignoreCase = true) }
        mediatorType1.postValue(filteredListT1)

        var filteredListT2 = tempListType2.filter { it.title!!.contains(title, ignoreCase = true) }
        mediatorType2.postValue(filteredListT2)
    }

    fun itemDone(item: Item) {
        viewModelScope.launch {
            useCase.itemPressedDone(item)
        }
    }

}