package com.sukriti.sukrititest

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.sukriti.sukrititest.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    var m: Int = 0

    lateinit var binding: ActivityFirstBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startbtn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val n = binding.initvaluetxt!!.getText().toString().trim { it <= ' ' }.toInt()
                if (n >= 0 && n <= 100) {
                    val intent = Intent(this@FirstActivity, SecondActivity::class.java)
                    intent.putExtra(
                        "initvalue",
                        binding.initvaluetxt!!.getText().toString().trim { it <= ' ' })
                    startActivityForResult(intent, 1003)
                } else {
                    showDialog(getResources().getString(R.string.allowd_range))
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1003) {
            if (resultCode == 1004) {
                m = data!!.getIntExtra("n", -1)

                binding.initvaluetxt!!.setText(m.toString())

                if (m < 40) {
                    showDialog(getResources().getString(R.string.failded))
                } else if (m < 70) {
                    showDialog(getResources().getString(R.string.average))
                } else if (m < 90) {
                    showDialog(getResources().getString(R.string.good))
                } else {
                    showDialog(getResources().getString(R.string.excellent))
                }
            }
        }
    }

    fun showDialog(msg: String?) {
        AlertDialog.Builder(this@FirstActivity)
            .setMessage(msg)
            .setPositiveButton("Ok", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    // Continue with delete operation
                }
            })
            .show()
    }
}

