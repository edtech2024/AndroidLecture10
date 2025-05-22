package com.example.myapplicationsixth.di

import android.os.Bundle
import com.example.domain.irepository.ItemRepository
import com.example.domain.usecase.IUseCase
import com.example.myapplicationsixth.fragment.DetailFragment
import com.example.myapplicationsixth.usecase.UseCaseImpl
import com.example.myapplicationsixth.viewmodel.DetailViewModel
import com.example.myapplicationsixth.viewmodel.ListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PresentationModule {

    @Singleton
    @Provides
    fun provideUseCaseImpl(repository: ItemRepository): IUseCase = UseCaseImpl(repository)

    @Provides
    fun provideListViewModel(useCase: IUseCase): ListViewModel = ListViewModel(useCase)

    @Provides
    fun provideDetailViewModel(useCase: IUseCase, bundle: Bundle, context: DetailFragment.OnItemCreateUpdateListener): DetailViewModel = DetailViewModel(useCase, bundle, context)

}