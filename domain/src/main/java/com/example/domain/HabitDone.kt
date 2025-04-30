package com.example.domain

import com.google.gson.annotations.SerializedName


data class HabitDone(
    @SerializedName("date")
    var date: Long,
    @SerializedName("habit_uid")
    var habit_uid: String
)