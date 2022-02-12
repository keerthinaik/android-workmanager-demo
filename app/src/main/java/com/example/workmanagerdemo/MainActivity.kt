package com.example.workmanagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager = WorkManager.getInstance(this)

        val data: Data = Data.Builder()
            .putInt("number", 5)
            .build()

        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val sampleOneTimeWorkRequest : OneTimeWorkRequest = OneTimeWorkRequest
            .Builder(SampleWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("sample_one_time_tag")
            .build()

        val periodicWorkRequest : PeriodicWorkRequest = PeriodicWorkRequest
            .Builder(SampleWorker::class.java, 3, TimeUnit.SECONDS)
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("sample_periodic_tag")
            .build()

//        workManager.enqueue(sampleOneTimeWorkRequest)
        workManager.enqueue(periodicWorkRequest)

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id).observe(this, Observer {
            Log.d(TAG, "SampleWorker work info is $it" )
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}