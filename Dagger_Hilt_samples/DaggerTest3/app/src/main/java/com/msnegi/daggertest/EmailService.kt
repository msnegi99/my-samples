package com.msnegi.daggertest

import android.util.Log
import javax.inject.Inject

interface NotificationService{
    fun send(to: String, from: String, body: String?)
}

class EmailService @Inject constructor() : NotificationService {
    override fun send(to: String, from: String, body: String?) {
        Log.d(TAG, "Email Sent")
    }
}

class MessageService @Inject constructor() : NotificationService{
    override fun send(to: String, from: String, body: String?) {
        Log.d(TAG, "Message Sent")
    }
}
