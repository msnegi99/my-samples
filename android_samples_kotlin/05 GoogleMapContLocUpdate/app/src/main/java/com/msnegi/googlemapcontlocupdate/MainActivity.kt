package com.msnegi.googlemapcontlocupdate

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.msnegi.googlemapcontlocupdate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        replaceFragment(HomeFragment(), Bundle(), "HomeFragment")
    }

    fun replaceFragment(fragment: Fragment, bundle: Bundle?, tag: String?) {
        if (bundle != null) {
            fragment.setArguments(bundle)
        }

        if (fragment is HomeFragment) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, tag)
                .commit()
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag)
                .addToBackStack(null).commit()
        }
    }

    override fun onBackPressed() {
        exitMessage()
    }

    private fun exitMessage() {
        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_exit_app)

        val btnYes = dialog.findViewById<View?>(R.id.btnYes) as Button
        btnYes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(arg0: View?) {
                dialog.dismiss()
                finish()
            }
        })

        val btnNo = dialog.findViewById<View?>(R.id.btnNo) as Button
        btnNo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(arg0: View?) {
                dialog.dismiss()
            }
        })

        dialog.show()
    }
}