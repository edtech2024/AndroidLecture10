package com.example.data.web

import com.example.domain.dataobject.HabitDone
import com.example.domain.dataobject.Item
import retrofit2.Response
import retrofit2.http.*

interface ItemApiInterface {

    @GET("habit")
    suspend fun getHabits():Response<List<Item>>

    @PUT("habit")
    suspend fun addHabit(@Body item: Item):Response<String>

    @PUT("habit")
    suspend fun updateHabit(@Body item: Item):Response<String>

    @POST("habit_done")
    suspend fun completeHabit(@Body habitDone: HabitDone):Response<String>

}