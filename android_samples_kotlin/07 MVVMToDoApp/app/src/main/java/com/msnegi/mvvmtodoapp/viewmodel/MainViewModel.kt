package com.msnegi.mvvmtodoapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
import com.msnegi.mvvmtodoapp.roomdb.Item
import com.msnegi.mvvmtodoapp.roomdb.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    var counter:MutableState<Int> = mutableStateOf(0)

    fun incrementCount(): Unit{
        counter.value = counter.value + 1
    }

    fun insertItem(item: Item){
        //viewModelScope.launch(Dispatchers.IO){
            itemRepository.insert(item)
        //}
    }

    fun getItems() : LiveData<List<Item>>{
        return itemRepository.getAll()
    }
}