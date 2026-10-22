package com.msnegi.intentserivceexample

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.text.TextUtils
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DownloadService : IntentService(DownloadService::class.java.getName())
{
    companion object {
        const val STATUS_RUNNING: Int = 0
        const val STATUS_FINISHED: Int = 1
        const val STATUS_ERROR: Int = 2

        private const val TAG = "DownloadService"
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("MSNEGI", "Service Started!")

        val receiver = intent?.getParcelableExtra<ResultReceiver?>("receiver")
        val url = intent?.getStringExtra("url")

        Log.d("MSNEGI", "receiver : " + receiver)
        Log.d("MSNEGI", "url : " + url)

        val bundle = Bundle()

        if (!TextUtils.isEmpty(url)) {
            /* Update UI: Download Service is Running */
            receiver!!.send(STATUS_RUNNING, Bundle.EMPTY)

            try {
                val results = downloadData(url)
                Log.d("MSNEGI", "results : " + results)

                /* Sending result back to activity */
                if (null != results && results.size > 0) {
                    bundle.putStringArray("result", results)
                    receiver.send(STATUS_FINISHED, bundle)
                }
            } catch (e: Exception) {
                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString())
                receiver.send(STATUS_ERROR, bundle)
            }
        }
        Log.d(TAG, "Service Stopping!")
        this.stopSelf()
    }

    @Throws(IOException::class, DownloadException::class)
    private fun downloadData(requestUrl: String?): Array<String?>? {
        var inputStream: InputStream? = null

        var urlConnection: HttpURLConnection? = null

        /* forming th java.net.URL object */
        val url = URL(requestUrl)

        urlConnection = url.openConnection() as HttpURLConnection?

        /* optional request header */
        urlConnection!!.setRequestProperty("Content-Type", "application/json")

        /* optional request header */
        urlConnection.setRequestProperty("Accept", "application/json")

        /* for Get request */
        urlConnection.setRequestMethod("GET")

        val statusCode = urlConnection.getResponseCode()

        Log.d("MSNEGI", "statusCode : " + statusCode)

        /* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = BufferedInputStream(urlConnection.getInputStream())

            val response = convertInputStreamToString(inputStream)

            val results = parseResult(response)

            return results
        } else {
            throw DownloadException("Failed to fetch data!!")
        }
    }

    @Throws(IOException::class)
    private fun convertInputStreamToString(inputStream: InputStream?): String {
        Log.d("MSNEGI", "convertInputStreamToString")

        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = ""
        var result = ""

        while ((bufferedReader.readLine().also { line = it }) != null) {
            result += line
        }

        Log.d("MSNEGI", "result : " + result)

        /* Close Stream */
        if (null != inputStream) {
            inputStream.close()
        }

        return result
    }

    private fun parseResult(result: String): Array<String?>? {
        Log.d("MSNEGI", "parseResult : result : " + result)

        var blogTitles: Array<String?>? = null

        try {
            val response = JSONObject(result)

            val posts = response.optJSONArray("product")

            blogTitles = arrayOfNulls<String>(posts!!.length())

            for (i in 0..<posts.length()) {
                val post = posts.optJSONObject(i)
                val title = post.optString("title")

                blogTitles[i] = title
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return blogTitles
    }

    inner class DownloadException : Exception {
        constructor(message: String?) : super(message)

        constructor(message: String?, cause: Throwable?) : super(message, cause)
    }

}