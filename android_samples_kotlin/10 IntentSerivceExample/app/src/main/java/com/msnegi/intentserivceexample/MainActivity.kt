package com.msnegi.intentserivceexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

//import android.support.v7.app.ActionBarActivity;
class MainActivity : Activity(), DownloadResultReceiver.Receiver
{
    private var listView: ListView? = null

    private var arrayAdapter: ArrayAdapter<*>? = null

    private var mReceiver: DownloadResultReceiver? = null

    val url: String = "https://mocki.io/v1/90d8a24d-5cd1-4361-b022-346c86b87423"
    //{"product":[{"id":1,"title":"Banana"},{"id":2,"title":"Mango"},{"id":3,"title":"Coconut"},{"id":4,"title":"Orange"}]}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Allow activity to show indeterminate progressbar */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)

        setContentView(R.layout.activity_main)
        listView = findViewById<View?>(R.id.listView) as ListView?

        /* Starting Download Service */
        mReceiver = DownloadResultReceiver(Handler())
        mReceiver!!.setReceiver(this)

        val intent = Intent(Intent.ACTION_SYNC, null, this, DownloadService::class.java)
        intent.putExtra("url", url)
        intent.putExtra("receiver", mReceiver)
        intent.putExtra("requestId", 101)

        startService(intent)
        Log.d("MSNEGI", "Starting service")
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        when (resultCode) {
            DownloadService.STATUS_RUNNING -> {
                setProgressBarIndeterminateVisibility(true)
            }
            DownloadService.STATUS_FINISHED -> {
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false)

                val results = resultData?.getStringArray("result")

                Log.d("MSNEGI", "UPDATE LIST VIEW : ")

                /* Update ListView with result */
                arrayAdapter = ArrayAdapter<String>(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    results?.toList() ?: emptyList()
                )
                listView!!.setAdapter(arrayAdapter)
            }

            DownloadService.STATUS_ERROR -> {
                /* Handle the error */
                val error = resultData?.getString(Intent.EXTRA_TEXT)
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }
    }

}