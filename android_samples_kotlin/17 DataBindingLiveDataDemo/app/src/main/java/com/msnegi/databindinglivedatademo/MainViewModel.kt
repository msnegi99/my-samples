package com.msnegi.databindinglivedatademo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel :ViewModel(){
    val quoteLiveData = MutableLiveData("this is description")

    fun updateQuote(){
        quoteLiveData.value = "updated description"
    }
}