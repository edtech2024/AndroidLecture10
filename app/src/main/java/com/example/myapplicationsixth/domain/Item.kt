package com.example.myapplicationsixth.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity(tableName = "items")
@Serializable
data class Item public constructor(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    var id: Int?,
    @SerializedName("uid")
    @SerialName("uid")
    @ColumnInfo(name = "uid")
    var uid: String?,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    @SerialName("title")
    var title: String?,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    @SerialName("description")
    var description: String?,
    @SerializedName("priority")
    @ColumnInfo(name = "priority")
    @SerialName("priority")
    var priority: Int?,
    @SerializedName("type")
    @ColumnInfo(name = "type")
    @SerialName("type")
    var type: Int?,
    @SerializedName("count")
    @ColumnInfo(name = "count")
    @SerialName("count")
    var count: Int?,
    @SerializedName("frequency")
    @ColumnInfo(name = "frequency")
    @SerialName("frequency")
    var frequency:Int?,
    @SerializedName("color")
    @ColumnInfo(name = "color")
    @SerialName("color")
    var color: Int?,
    @SerializedName("date")
    @ColumnInfo(name = "date")
    @SerialName("date")
    var date: Long?
) {
    companion object {
        operator fun invoke(
            id: Int?,
            uid: String?,
            title: String?,
            description: String?,
            priority: Int?,
            type: Int?,
            count: Int?,
            frequency: Int?,
            color: Int?,
            date: Long?
        ) = Item (
            id = id ?: null,
            uid = uid ?: "",
            title = title ?: "Title",
            description = description ?: "Description",
            priority = priority ?: 0,
            type = type ?: 0,
            count = count ?: 0,
            frequency = frequency ?: 0,
            color = color ?: 0,
            date = date ?: 0
        )
    }
}