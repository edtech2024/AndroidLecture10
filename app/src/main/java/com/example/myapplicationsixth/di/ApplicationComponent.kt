package com.example.myapplicationsixth.di

import android.content.Context
import com.example.data.di.DataModule
import com.example.domain.di.DomainModule
import com.example.myapplicationsixth.fragment.DetailFragment
import com.example.myapplicationsixth.fragment.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class] )
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface SingletonComponentFactory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(fragment: MainFragment)
    fun inject(fragment: DetailFragment)

}