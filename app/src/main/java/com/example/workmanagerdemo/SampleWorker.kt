package com.example.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SampleWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val number:Int = inputData.getInt("number", -1)

        if (number < 0) {
            return Result.failure()
        }

        for (i in 0..number) {
            Log.d(TAG, "number is $i")
            Thread.sleep(1000)
        }
        return Result.success()
    }

    companion object {
        private const val TAG = "SampleWorker"
    }
}