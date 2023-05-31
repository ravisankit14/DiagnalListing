package com.ravi.diagnal.di.module

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.ravi.diagnal.di.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideViewModelFactory(): ViewModelProvider.AndroidViewModelFactory =
        ViewModelFactory(application)
}