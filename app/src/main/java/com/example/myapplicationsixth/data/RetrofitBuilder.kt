package com.example.myapplicationsixth.data

import retrofit2.Retrofit


abstract class RetrofitBuilder {

    companion object {

        @Volatile
        private lateinit var INSTANCE: Retrofit.Builder

        fun getRetrofitBuilder(): Retrofit.Builder {

            val retrofit = Retrofit.Builder()

            RetrofitBuilder.INSTANCE = retrofit

            return RetrofitBuilder.INSTANCE
        }
    }
}