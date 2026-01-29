package com.msnegi.mvvmtodoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msnegi.mvvmtodoapp.roomdb.ItemRepository

class MainViewModelFactory(private val quoteRepository: ItemRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // You can also add a check to ensure you're creating the correct ViewModel
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(quoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}