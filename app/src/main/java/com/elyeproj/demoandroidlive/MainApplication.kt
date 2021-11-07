package com.elyeproj.demoandroidlive

import android.app.Application
import android.os.Bundle

class MainApplication: Application(), ActivityLifecycleHandler.LifecycleListener {

    companion object {
        const val APPLICATION_SAVE_KEY = "application_bundle"
        var myVariable = false
        var myStoredVariable = false
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleHandler(this))
    }

    override fun onApplicationCreated(savedInstanceState: Bundle?) {
        savedInstanceState?.apply {
            myStoredVariable = getBoolean(APPLICATION_SAVE_KEY)
        }
    }

    override fun onApplicationSaveInstanceState(savedInstanceState: Bundle) {
        super.onApplicationSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean(APPLICATION_SAVE_KEY, myStoredVariable)
    }
}
