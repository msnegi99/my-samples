package com.msnegi.usinghiltexample.cars

import android.util.Log
import javax.inject.Inject

class Engine {

    @Inject
    constructor()

    fun getEngine()
    {
        Log.d("Cars", "engine started...");
    }
}