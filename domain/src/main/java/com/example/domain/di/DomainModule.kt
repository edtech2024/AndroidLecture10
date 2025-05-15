package com.example.domain.di

import com.example.domain.irepository.ItemRepository
import com.example.domain.usecase.UseCase
import dagger.Module
import dagger.Provides


@Module
class DomainModule {

    @Provides
    fun provideUseCase(repository: ItemRepository): UseCase = UseCase(repository)

}