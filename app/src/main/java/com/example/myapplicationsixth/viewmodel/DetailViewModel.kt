package com.example.myapplicationsixth.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataobject.Item
import com.example.domain.converter.StringValue
import com.example.domain.usecase.IUseCase
import com.example.domain.usecase.UseCase
import com.example.myapplicationsixth.fragment.DetailFragment
import com.example.myapplicationsixth.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DetailViewModel @Inject constructor(val useCase: IUseCase, val bundle: Bundle?, val context: DetailFragment.OnItemCreateUpdateListener?) : ViewModel() {

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
            //title = argTitle, description = argDescription,
            title = title.value, description = argDescription,
            priority = argPriority,
            type = argType.toInt(),
            count = argCount?.toInt(),
            frequency = argFrequency?.toInt(),
            color = 0, date = argDate.toString().toLong(),
            done_dates = argDoneDatesL
        )

/*        val item = Item(id = argId, uid = argUid,
            title = title.value, description = description.value,
            priority = priority.value.toInt(),
            type = type.value.toInt(),
            count = count.value.toInt(),
            frequency = frequency.value.toInt(),
            color = 0, date = argDate.toString().toLong(),
            done_dates = argDoneDatesL
        )*/

        return item
    }

    private val _action: MutableStateFlow<String> = MutableStateFlow("")
    val action: StateFlow<String> = _action.asStateFlow()

    fun setAction(action: String) {
        _action.value = action
    }

    private val _id: MutableStateFlow<String> = MutableStateFlow("")
    val id: StateFlow<String> = _id.asStateFlow()

    fun setId(id: String) {
        _id.value = id
    }

    private val _uid: MutableStateFlow<String> = MutableStateFlow("")
    val uid: StateFlow<String> = _uid.asStateFlow()

    fun setUid(uid: String) {
        _uid.value = uid
    }

    private val _title: MutableStateFlow<String> = MutableStateFlow("Title")
    val title: StateFlow<String> = _title.asStateFlow()

    fun setTitle(title: String) {
        _title.value = title
    }

    private val _description: MutableStateFlow<String> = MutableStateFlow("Description")
    val description: StateFlow<String> = _description.asStateFlow()

    fun setDescription(description: String) {
        _description.value = description
    }

    private val _priority: MutableStateFlow<String> = MutableStateFlow("0")
    val priority: StateFlow<String> = _priority.asStateFlow()

    fun setPriority(priority: String) {
        _priority.value = priority
    }

    private val _type: MutableStateFlow<String> = MutableStateFlow("0")
    val type: StateFlow<String> = _type.asStateFlow()

    fun setType(type: String) {
        _type.value = type
    }

    private val _count: MutableStateFlow<String> = MutableStateFlow("0")
    val count: StateFlow<String> = _count.asStateFlow()

    fun setCount(count: String) {
        _count.value = count
    }

    private val _frequency: MutableStateFlow<String> = MutableStateFlow("0")
    val frequency: StateFlow<String> = _frequency.asStateFlow()

    fun setFrequency(frequency: String) {
        _frequency.value = frequency
    }

    private val _color: MutableStateFlow<String> = MutableStateFlow("0")
    val color: StateFlow<String> = _color.asStateFlow()

    fun setColor(color: String) {
        _color.value = color
    }

    private val _date: MutableStateFlow<String> = MutableStateFlow("")
    val date: StateFlow<String> = _date.asStateFlow()

    fun setDate(date: String) {
        _date.value = date
    }

    private val _done_dates: MutableStateFlow<String> = MutableStateFlow("")
    val done_dates: StateFlow<String> = _done_dates.asStateFlow()

    fun setDone_dates(done_dates: String) {
        _done_dates.value = done_dates
    }

}