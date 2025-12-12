package com.msnegi.usinghiltexample.cars

import android.util.Log
import javax.inject.Inject

class Car @Inject constructor(private val wheel: Wheel, private val engine: Engine){

    fun getCar(){
        Log.d("Cars", "Car is running")

        engine.getEngine()
        wheel.getWheel()
    }
}