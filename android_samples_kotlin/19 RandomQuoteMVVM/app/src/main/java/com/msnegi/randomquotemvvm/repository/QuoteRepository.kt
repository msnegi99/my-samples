package com.msnegi.randomquotemvvm.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.msnegi.randomquotemvvm.api.QuoteService
import com.msnegi.randomquotemvvm.db.QuoteDatabase
import com.msnegi.randomquotemvvm.models.QuoteList
import com.msnegi.randomquotemvvm.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val quotesLiveData = MutableLiveData<QuoteList>()

    val quotes: LiveData<QuoteList>
    get() = quotesLiveData

    suspend fun getQuotes(page: Int){

        //API need to setup. switch off the internet connection, app will work

        if(NetworkUtils.isInternetAvailable(applicationContext)){
            val result = quoteService.getQuotes(page)
            if(result?.body() != null){
                quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                quotesLiveData.postValue(result.body())
            }
        }
        else{
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1,1,1,quotes, 1, 1)
            quotesLiveData.postValue(quoteList)
        }

    }
}







