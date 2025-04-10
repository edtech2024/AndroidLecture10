package com.example.myapplicationsixth.presentation

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationsixth.R
import com.example.myapplicationsixth.data.ItemRepository
import com.example.myapplicationsixth.domain.Item
import com.example.myapplicationsixth.domain.StringValue
import kotlinx.coroutines.launch


class DetailViewModel(val repository: ItemRepository, val bundle: Bundle?, val context: DetailFragment.OnItemCreateUpdateListener?) : ViewModel() {

    var argAction: String? = null
    var argId: Int? = null
    var argTitle: String = "null"
    var argDescription: String = "null"
    var argPriority: Int? = null
    var argType: String = "null"
    var argType1: Boolean
    var argType2: Boolean
    var argCount: String = "null"
    var argPeriod: String = "null"

    var flag: Boolean

    init{
        flag = true
        argType1 = false
        argType2 = false

        if (bundle != null) {
            readBundle(bundle)
        }
    }

    fun chooseTypeFirst(){
        argType1 = true
        argType2 = false
        argType =  StringValue.StringResource(R.string.type_1).asString(context as Context)
    }

    fun chooseTypeSecond(){
        argType1 = false
        argType2 = true
        argType = StringValue.StringResource(R.string.type_2).asString(context as Context)
    }

    private fun readBundle(bundle:Bundle){
        argAction = bundle.getString(StringValue.StringResource(R.string.action).asString(context as Context), "")
        argId = bundle.getInt(StringValue.StringResource(R.string.id).asString(context as Context),0 )
        argTitle = bundle.getString(StringValue.StringResource(R.string.title).asString(context as Context), "Naming")
        argDescription = bundle.getString(StringValue.StringResource(R.string.description).asString(context as Context), "...")
        argPriority = bundle.getString(StringValue.StringResource(R.string.priority).asString(context as Context), "2")?.toInt()
        argType = bundle.getString(StringValue.StringResource(R.string.type).asString(context as Context), "Type 1")

        if (argType != StringValue.StringResource(R.string.type_1).asString(context as Context)) {
            argType2 = true
            argType1 = false
        }
        if (argType != StringValue.StringResource(R.string.type_2).asString(context as Context)) {
            argType1 = true
            argType2 = false
        }

        argCount = bundle.getString(StringValue.StringResource(R.string.count).asString(context as Context), "0")
        argPeriod = bundle.getString(StringValue.StringResource(R.string.period).asString(context as Context), "0")

    }

    override fun onCleared() {}

    private fun callCreateMethod(item: Item){
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    private fun callUpdateMethod(item: Item){
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    fun callClick(){
        if (argAction == StringValue.StringResource(R.string.create).asString(context as Context)){
            callCreateMethod(makeItem())
        } else {
            callUpdateMethod(makeItem())
        }
    }

    private fun makeItem(): Item {

        if (argAction == StringValue.StringResource(R.string.create).asString(context as Context)) {
            argId = null
        }

        val item = Item(argId, argTitle, argDescription,
            argPriority, argType, argCount?.toInt(), argPeriod?.toInt()
        )

        return item
    }

}