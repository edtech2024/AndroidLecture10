package com.example.myapplicationsixth.di

import android.os.Bundle
import com.example.domain.usecase.UseCase
import com.example.myapplicationsixth.fragment.DetailFragment
import com.example.myapplicationsixth.viewmodel.DetailViewModel
import com.example.myapplicationsixth.viewmodel.ListViewModel
import dagger.Module
import dagger.Provides


@Module
class PresentationModule {

    @Provides
    fun provideListViewModel(useCase: UseCase): ListViewModel = ListViewModel(useCase)

    @Provides
    fun provideDetailViewModel(useCase: UseCase, bundle: Bundle, context: DetailFragment.OnItemCreateUpdateListener): DetailViewModel = DetailViewModel(useCase, bundle, context)

}