package com.example.myapplicationsixth

import android.app.Application
import com.example.myapplicationsixth.data.*
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemApplication : Application() {

    val authInterceptor by lazy { AuthInterceptor() }

    val loggingInterceptor by lazy {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY) }

    val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    val BASE_URL by lazy { "https://droid-test-server.doubletapp.ru/api/" }

    val retrofitBuilder by lazy { RetrofitBuilder.getRetrofitBuilder() }

    val retrofit: Retrofit by lazy {
        retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // Using by lazy so the database and the repository are only created when they're needed rather than when the application starts
    val database by lazy { ItemDatabase.getDatabase(this) }

    val dispatcher by lazy { Dispatchers.IO }

    val repository by lazy {
        ItemRepository(
            database.itemDao(),
            retrofit.create(ItemApiInterface::class.java),
            dispatcher
        )
    }

}