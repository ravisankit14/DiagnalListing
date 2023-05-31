package com.ravi.diagnal.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ravi.diagnal.ui.HomeListingViewModel

class ViewModelFactory(private val application: Application): ViewModelProvider.AndroidViewModelFactory(Application()) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeListingViewModel::class.java)) {
            return HomeListingViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}