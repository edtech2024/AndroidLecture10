package com.example.data

import android.content.Context
import com.example.domain.ItemRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
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
        .build()

    @Singleton
    @Provides
    fun provideBaseUrl(): String = "https://droid-test-server.doubletapp.ru/api/"

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(BASE_URL: String,
                               gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideAuthApiService(okHttpClient: OkHttpClient,
                              retrofit: Builder
    ): ItemApiInterface = retrofit
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
    fun provideRepositoryImpl(database: ItemDatabase,
                              itemApiService: ItemApiInterface,
                              dispatcher: CoroutineDispatcher
    ): ItemRepository = ItemRepositoryImpl(
        database.itemDao(),
        itemApiService,
        dispatcher
    )

}