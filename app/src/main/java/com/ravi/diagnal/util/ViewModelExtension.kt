package com.ravi.diagnal.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ravi.diagnal.ui.HomeListingActivity

inline fun <reified T : ViewModel> HomeListingActivity.getViewModel(): T {
    return ViewModelProvider(this, viewModelFactory)[T::class.java]
}