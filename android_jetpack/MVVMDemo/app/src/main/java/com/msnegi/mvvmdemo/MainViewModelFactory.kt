package com.msnegi.mvvmdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val quoteRepository: QuoteRepository) : ViewModelProvider.Factory {
    // Add the 'override' keyword and use the correct non-nullable generic type 'T : ViewModel'
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // You can also add a check to ensure you're creating the correct ViewModel
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(quoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}