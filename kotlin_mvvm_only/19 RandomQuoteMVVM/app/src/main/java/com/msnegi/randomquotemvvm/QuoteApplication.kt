package com.msnegi.randomquotemvvm

import android.app.Application
import com.msnegi.randomquotemvvm.api.QuoteService
import com.msnegi.randomquotemvvm.api.RetrofitHelper
import com.msnegi.randomquotemvvm.db.QuoteDatabase
import com.msnegi.randomquotemvvm.repository.QuoteRepository

class QuoteApplication : Application() {

    lateinit var quoteRepository: QuoteRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRepository = QuoteRepository(quoteService, database, applicationContext)
    }
}