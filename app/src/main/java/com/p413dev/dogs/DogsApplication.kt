package com.p413dev.dogs

import android.app.Application
import android.content.Context
import com.androidnetworking.AndroidNetworking

/**
 * Created by srujan.gade on 22/09/2019
 */

class DogsApplication : Application() {

    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        AndroidNetworking.initialize(applicationContext);
    }
}