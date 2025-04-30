package com.example.domain

import dagger.Module
import dagger.Provides


@Module
class DomainModule {

    @Provides
    fun provideUseCase(repository: ItemRepository): UseCase = UseCase(repository)

}