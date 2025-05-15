package com.example.data.di

import android.content.Context
import com.example.data.repositoryimpl.ItemRepositoryImpl
import com.example.data.database.ItemDatabase
import com.example.data.web.AuthInterceptor
import com.example.data.web.ItemApiInterface
import com.example.domain.irepository.ItemRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
class DataModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor,
                            httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .retryOnConnectionFailure(true)
        .build()

    @Singleton
    @Provides
    fun provideBaseUrl(): String = "https://droid-test-server.doubletapp.ru/api/"

    @Singleton
    @Provides
    fun provideAuthApiService(BASE_URL: String,
                              okHttpClient: OkHttpClient
    ): ItemApiInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ItemApiInterface::class.java)

    @Singleton
    @Provides
    fun provideDatabase(context: Context): ItemDatabase = ItemDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideRefreshIntervalMs(): Long = 50000

    @Singleton
    @Provides
    fun provideRepositoryImpl(database: ItemDatabase,
                              itemApiService: ItemApiInterface,
                              dispatcher: CoroutineDispatcher,
                              refreshIntervalMs: Long
    ): ItemRepository = ItemRepositoryImpl(
        database.itemDao(),
        itemApiService,
        dispatcher,
        refreshIntervalMs
    )

}