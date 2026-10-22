package com.msnegi.mvvmtodoapp

import com.msnegi.mvvmtodoapp.roomdb.Item

interface CallBackInterface {
    fun editItem(taskItem: Item?)
    fun deleteItem(taskItem: Item?)
}