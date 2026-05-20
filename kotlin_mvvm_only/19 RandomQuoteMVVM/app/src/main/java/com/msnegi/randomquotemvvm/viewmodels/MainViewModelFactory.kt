package com.msnegi.randomquotemvvm.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msnegi.randomquotemvvm.repository.QuoteRepository

class MainViewModelFactory(val context: Context, private val repository: QuoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context, repository) as T
    }
}