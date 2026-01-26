package com.msnegi.usinghiltexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msnegi.usinghiltexample.cars.Car
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

 @AndroidEntryPoint
 class MainActivity : AppCompatActivity() {

    @Inject lateinit var car:Car
    //@Inject lateinit var main:Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        car.getCar()
        //main.getName()
    }
}