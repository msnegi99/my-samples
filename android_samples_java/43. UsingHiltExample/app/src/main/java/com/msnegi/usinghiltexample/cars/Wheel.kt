package com.msnegi.usinghiltexample.cars

import android.util.Log
import javax.inject.Inject

class Wheel {

    @Inject
    constructor()

    fun getWheel()
    {
        Log.d("Cars", "Weel started...");
    }
}