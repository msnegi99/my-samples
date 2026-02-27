package com.msnegi.daggertest

import dagger.Component

@Component
interface UserRegistrationComponent {

    fun getUserRegistrationService() : UserRegistrationService

    fun getEmailService() : EmailService
}