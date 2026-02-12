package com.msnegi.callbackexamples

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), CallBackInterface {
    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null
    var receiver: MessageReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiver = MessageReceiver()
        receiver!!.setContext(this)
        ContextCompat.registerReceiver(
            this,
            receiver,
            IntentFilter("please_call_back"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        btn1 = findViewById<View?>(R.id.btnBroadcast) as Button
        btn1!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                sendBroadcast(Intent("please_call_back").setPackage(getPackageName()))
            }
        })

        btn2 = findViewById<View?>(R.id.btnActivity) as Button
        btn2!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity, CallBackActivity::class.java))
            }
        })

        btn3 = findViewById<View?>(R.id.btnFragment) as Button
        btn3!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val newFragment = CallbackFragment()

                val transaction = getSupportFragmentManager().beginTransaction()
                transaction.replace(R.id.llFragmentContainer, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        getApplication().unregisterReceiver(receiver)
    }

    override fun callbackfunction(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
