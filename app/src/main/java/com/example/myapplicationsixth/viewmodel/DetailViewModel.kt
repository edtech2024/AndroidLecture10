package com.example.myapplicationsixth.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataobject.Item
import com.example.domain.converter.StringValue
import com.example.domain.usecase.UseCase
import com.example.myapplicationsixth.fragment.DetailFragment
import com.example.myapplicationsixth.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DetailViewModel @Inject constructor(val useCase: UseCase, val bundle: Bundle?, val context: DetailFragment.OnItemCreateUpdateListener?) : ViewModel() {

    var argAction: String? = null
    var argId: Int? = null
    var argTitle: String = ""
    var argDescription: String = "null"
    var argPriority: Int? = null
    var argType: Int = 0
    var argType1: Boolean
    var argType2: Boolean
    var argCount: String = "null"
    var argFrequency: String = "0"
    var argColor: String = "0"
    var argUid: String = ""

    var simpleDateFormat = SimpleDateFormat("ddMMyyyyHHmmss")
    var argDate: Long = simpleDateFormat.format(Date()).toLong()
    var argDateTime: String = simpleDateFormat.format(Date()).toString()

    var argDoneDatesL: List<Long> = listOf()
    var argDoneDates: String = ""

    init{
        argType1 = false
        argType2 = false

        if (bundle != null) {
            readBundle(bundle)
        }
    }

    fun chooseTypeFirst(){
        argType1 = true
        argType2 = false
        argType =  0
    }

    fun chooseTypeSecond(){
        argType1 = false
        argType2 = true
        argType = 1
    }

    private fun readBundle(bundle:Bundle){
        extractingItemValues(bundle)
        settingItemType()
    }

    private fun extractingItemValues(bundle:Bundle) {
        argAction = bundle.getString(StringValue.StringResource(R.string.action).asString(context as Context), "")
        argId = bundle.getInt(StringValue.StringResource(R.string.id).asString(context as Context),0 )
        argTitle = bundle.getString(StringValue.StringResource(R.string.title).asString(context as Context), "Title")
        argDescription = bundle.getString(StringValue.StringResource(R.string.description).asString(context as Context), "Description")
        argPriority = bundle.getString(StringValue.StringResource(R.string.priority).asString(context as Context), "2")?.toInt()
        argType = bundle.getString(StringValue.StringResource(R.string.type).asString(context as Context), "0").toInt()
        argCount = bundle.getString(StringValue.StringResource(R.string.count).asString(context as Context), "0")
        argFrequency = bundle.getString(StringValue.StringResource(R.string.frequency).asString(context as Context), "0")
        argColor = bundle.getString(StringValue.StringResource(R.string.color).asString(context as Context), "0")
        argUid = bundle.getString(StringValue.StringResource(R.string.uid).asString(context as Context), "")
        argDoneDates = bundle.getString(StringValue.StringResource(R.string.done_dates).asString(context as Context), "0").toString()
    }

    private fun settingItemType(){
        if (argType != 0) {
            argType2 = true
            argType1 = false
        }
        if (argType != 1) {
            argType1 = true
            argType2 = false
        }
    }

    override fun onCleared() {}

    // Launching a new coroutine to insert the data in a non-blocking way
    private fun clickButtonCreateItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(NonCancellable) {
                useCase.create(item)
            }
        }
    }

    // Launching a new coroutine to update the data in a non-blocking way
    private fun clickButtonUpdateItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(NonCancellable) {
                useCase.update(item)
            }
        }
    }

    fun touchButton(){
        if (argAction == StringValue.StringResource(R.string.create).asString(context as Context)){
            clickButtonCreateItem(makeItem())
        } else {
            clickButtonUpdateItem(makeItem())
        }
    }

    private fun makeItem(): Item {

        val item = Item(id = argId, uid = argUid,
            title = argTitle, description = argDescription,
            priority = argPriority,
            type = argType.toInt(),
            count = argCount?.toInt(),
            frequency = argFrequency?.toInt(),
            color = 0, date = argDate.toString().toLong(),
            done_dates = argDoneDatesL
        )

        return item
    }

}