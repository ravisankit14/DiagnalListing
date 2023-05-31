package com.ravi.diagnal

import android.app.Application
import com.ravi.diagnal.di.component.AppComponent
import com.ravi.diagnal.di.component.DaggerAppComponent
import com.ravi.diagnal.di.module.AppModule

class DiagnalApplication :  Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                       .appModule(AppModule(this))
                       .build()

    }
}