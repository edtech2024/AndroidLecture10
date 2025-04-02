package com.example.myapplicationsixth.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "items")
@Serializable
data class Item public constructor(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    var id: Int?,
    @ColumnInfo(name = "title")
    @SerialName("title")
    var title: String?,
    @ColumnInfo(name = "description")
    @SerialName("description")
    var description: String?,
    @ColumnInfo(name = "priority")
    @SerialName("priority")
    var priority: Int?,
    @ColumnInfo(name = "type")
    @SerialName("type")
    var type: String?,
    @ColumnInfo(name = "count")
    @SerialName("count")
    var count: Int?,
    @ColumnInfo(name = "period")
    @SerialName("period")
    var period: Int?
) {
    companion object {
        operator fun invoke(
            id: Int?,
            title: String?,
            description: String?,
            priority: Int?,
            type: String?,
            count: Int?,
            period: Int?
        ) = Item (
            id = id ?: 0,
            title = title ?: "Title",
            description = description ?: "",
            priority = priority ?: 0,
            type = type ?: "Type 1",
            count = count ?: 0,
            period = period ?: 0
        )
    }
}