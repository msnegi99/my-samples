package com.msnegi.daggertest

import dagger.Binds
import dagger.Module
import dagger.Provides

/*
@Module
class NotificationServiceModule {

    @Provides
    fun getMessageService() : NotificationService {
        return MessageService()
    }
}*/

@Module
abstract class NotificationServiceModule {

    @Binds
    abstract fun getMessageService(messageService: MessageService) : NotificationService
}
