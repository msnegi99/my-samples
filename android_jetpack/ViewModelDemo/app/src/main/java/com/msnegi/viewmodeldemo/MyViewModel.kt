package com.msnegi.viewmodeldemo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    var counter:MutableState<Int> = mutableStateOf(0)

    fun incrementCount(): Unit{
        counter.value = counter.value + 1
    }
}