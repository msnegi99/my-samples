package com.msnegi.daggertest

import dagger.Component

@Component
interface UserRegistrationComponent {

    fun inject(mainActivity: MainActivity)
}