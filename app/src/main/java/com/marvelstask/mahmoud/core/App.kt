package com.marvelstask.mahmoud.core

import android.app.Application
import android.content.Context
import androidx.work.*
import com.marvelstask.mahmoud.R
import com.marvelstask.mahmoud.presenter.MainPresenter
import com.marvelstask.mahmoud.works.DailyWork
import java.util.concurrent.TimeUnit


class App : Application() {
    val TAG = App::class.java.simpleName
    override fun onCreate() {
        super.onCreate()
        loadDataOnceADay()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    //load the data once a day
    //the work request is to read data from marvel api and save it to offline database as required
    private fun loadDataOnceADay() {
        val workUniqueName = getString(R.string.load_data_once)
        val workManager = WorkManager.getInstance(applicationContext)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //for this task also we can make recursive worker using OneTimeWorkRequestBuilder
        val loadDataWorkRequest =
            //it will run once a day continuously
            PeriodicWorkRequestBuilder<DailyWork>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                //work will start after 1 day
                .setInitialDelay(1, TimeUnit.DAYS)
                .build()

        //enqueue the work request
        workManager
            .enqueueUniquePeriodicWork(
                workUniqueName,
                ExistingPeriodicWorkPolicy.KEEP,
                loadDataWorkRequest
            )
    }


}