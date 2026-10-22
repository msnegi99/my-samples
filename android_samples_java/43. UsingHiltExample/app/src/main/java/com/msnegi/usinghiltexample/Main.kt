package com.msnegi.usinghiltexample

import javax.inject.Inject
import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton

interface One{
    fun getName()
}

class ImplementOne @Inject constructor(): One{
    override fun getName(){
        Log.d("Cars", "my name is jayant")
    }
}

class Main @Inject constructor(private val one:One){
    fun getName()
    {
        one.getName()
    }
}

/*
@Module
@InstallIn(MyApplication::class)             //it means we can use our module any where
abstract class AppModule(){

    @Binds
    @Singleton  // scope
    abstract fun binding(
        implementOne: ImplementOne
    ):One
}
*/
