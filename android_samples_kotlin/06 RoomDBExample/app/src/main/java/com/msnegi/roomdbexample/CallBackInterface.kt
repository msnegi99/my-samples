package com.msnegi.roomdbexample

import com.msnegi.roomdbexample.roomdb.Item

interface CallBackInterface {
    fun editItem(taskItem: Item?)
    fun deleteItem(taskItem: Item?)
}