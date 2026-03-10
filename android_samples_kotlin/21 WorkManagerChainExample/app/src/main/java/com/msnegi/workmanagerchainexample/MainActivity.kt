package com.msnegi.workmanagerchainexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var startbtn:Button
    lateinit var stopbtn:Button

    lateinit var workManager:WorkManager
    lateinit var workRequest1: OneTimeWorkRequest
    lateinit var workRequest2: OneTimeWorkRequest
    lateinit var workRequest3: OneTimeWorkRequest

    var mStop = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager = WorkManager.getInstance()
        workRequest1 = OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker::class.java).addTag("worker1").build()
        workRequest2 = OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker::class.java).addTag("worker2").build()
        workRequest3 = OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker::class.java).addTag("worker3").build()

        startbtn = findViewById(R.id.startbtn) as Button
        stopbtn = findViewById(R.id.stopbtn) as Button

        startbtn.setOnClickListener {
            mStop = true;
            workManager.beginWith(workRequest1).then(workRequest2).then(workRequest3).enqueue()
        }

        stopbtn.setOnClickListener {
            workManager.cancelAllWorkByTag("worker3")
        }
    }
}