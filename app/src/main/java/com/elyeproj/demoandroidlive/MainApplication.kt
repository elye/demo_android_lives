package com.elyeproj.demoandroidlive

import android.app.Activity
import android.app.Application
import android.os.Bundle

class MainApplication: Application() {

    companion object {
        const val APPLICATION_SAVE_KEY = "application_bundle"
        var myVariable = false
        var myStoredVariable = false
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                savedInstanceState?.apply {
                    myStoredVariable = getBoolean(APPLICATION_SAVE_KEY)
                }
            }
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle) {
                savedInstanceState.putBoolean(APPLICATION_SAVE_KEY, myStoredVariable)
            }
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}