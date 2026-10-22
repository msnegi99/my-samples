package com.msnegi.daggertest

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/*
@Module
class NotificationServiceModule {

    @Provides
    fun getMessageService() : NotificationService {
        return MessageService()
    }
}*/

@Module
class NotificationServiceModule() {

    @ApplicationScope
    @MessageQualifier
    @Provides
    fun getMessageService(retryCount: Int) : NotificationService {
        return MessageService(retryCount)
    }

    @Named("email")
    @Provides
    fun getEmailService(emailService: EmailService) : NotificationService {
        return emailService
    }
}
