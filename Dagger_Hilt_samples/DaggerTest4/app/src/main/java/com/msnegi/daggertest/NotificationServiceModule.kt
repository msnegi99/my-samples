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
class NotificationServiceModule {

    @MessageQualifier
    @Provides
    fun getMessageService() : NotificationService {
        return MessageService()
    }

    @Named("email")
    @Provides
    fun getEmailService(emailService: EmailService) : NotificationService {
        return emailService
    }
}
