package com.elyeproj.demoandroidlive

import android.app.Application

class MainApplication: Application() {

    companion object {
        var myVariable = false
    }

    override fun onCreate() {
        super.onCreate()
    }
}