package com.msnegi.daggertest

import android.app.Application
import com.msnegi.daggertest.DaggerUserRegistrationComponent

class UserApplication : Application() {

    lateinit var userRegistrationComponent: UserRegistrationComponent

    override fun onCreate() {
        super.onCreate()
        userRegistrationComponent = DaggerUserRegistrationComponent.factory().create(3)
    }
}