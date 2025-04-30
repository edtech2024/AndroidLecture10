package com.example.myapplicationsixth

import android.content.Context
import com.example.data.DataModule
import com.example.domain.DomainModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class] )
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface SingletonComponentFactory {
        fun create(@BindsInstance context: Context):ApplicationComponent
    }

    fun inject(fragment: MainFragment)
    fun inject(fragment: DetailFragment)

}