package com.msnegi.testapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    var counter:MutableState<Int> = mutableStateOf(0);

    fun incrementCount(){
        counter.value = counter.value + 1;
    }
}