package com.example.myapplicationsixth

import android.app.Application
import com.example.myapplicationsixth.di.ApplicationComponent
import com.example.myapplicationsixth.di.DaggerApplicationComponent


class ItemApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.factory().create(applicationContext)
    }

}