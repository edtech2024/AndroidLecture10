package com.example.data

import retrofit2.Retrofit


abstract class RetrofitBuilder {

    companion object {

        @Volatile
        private lateinit var INSTANCE: Retrofit.Builder

        fun getRetrofitBuilder(): Retrofit.Builder {

            val retrofit = Retrofit.Builder()

            INSTANCE = retrofit

            return INSTANCE
        }
    }
}