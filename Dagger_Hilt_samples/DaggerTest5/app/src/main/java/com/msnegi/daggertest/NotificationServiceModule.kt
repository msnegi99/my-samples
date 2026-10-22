package com.msnegi.daggertest

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

/*
@Module
class NotificationServiceModule {

    @Provides
    fun getMessageService() : NotificationService {
        return MessageService()
    }
}*/

@Module
class NotificationServiceModule(private val retryCount: Int) {

    @MessageQualifier
    @Provides
    fun getMessageService() : NotificationService {
        return MessageService(retryCount)
    }

    @Named("email")
    @Provides
    fun getEmailService(emailService: EmailService) : NotificationService {
        return emailService
    }
}
