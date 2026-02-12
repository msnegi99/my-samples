package com.msnegi.callbackexamples

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CallBackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_back)

        val txtHello = findViewById<View?>(R.id.txtHello) as TextView
        txtHello.setText(
            "An activity can't make any callback to calling activity. " +
                    "An activity return control to calling activity through onActivityResult() function only."
        )
    }
}
