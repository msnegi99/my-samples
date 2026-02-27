package com.msnegi.daggertest

import android.util.Log
import javax.inject.Inject

class UserRepository @Inject constructor() {

    fun saveUser(email: String, password: String){
        Log.d(TAG,"user saved in DB")
    }
}