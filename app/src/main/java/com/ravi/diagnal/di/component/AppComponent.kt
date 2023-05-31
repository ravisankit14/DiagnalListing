package com.ravi.diagnal.di.component

import com.ravi.diagnal.di.module.AppModule
import com.ravi.diagnal.ui.HomeListingActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: HomeListingActivity)

}