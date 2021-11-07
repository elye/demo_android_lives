package com.elyeproj.demoandroidlive

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle

/**
 * Originally based of from
 * https://www.klaasnotfound.com/2015/08/24/tracking-the-application-lifecycle-on-android/
 * with the code sample from
 * https://gist.github.com/klaasnotfound/e14adefddaf72b941ef4e4245edca7e4
 * 
 * A convenience lifecycle handler that tracks whether the overall application is
 * started, in the foreground, in the background or stopped and ignores transitions
 * between individual activities.
 */
class ActivityLifecycleHandler(private val listener: LifecycleListener?) :
    Application.ActivityLifecycleCallbacks {

    // Informs the listener about application lifecycle events.
    interface LifecycleListener {

        // Called right after the application is created
        fun onApplicationCreated(savedInstanceState: Bundle?) {}

        // Called right before the application is stopped.
        fun onApplicationStopped() {}

        // Called right after the application has been started.
        fun onApplicationStarted() {}

        // Called when the application is paused (but still awake).
        fun onApplicationPaused() {}

        // Called right after the application has been resumed (come to the foreground).
        fun onApplicationResumed() {}

        // Called right after the application is stopped (for Android P and later)
        // Called right before onStop or onPause (for before Android P)
        // https://developer.android.com/reference/android/app/Activity#onSaveInstanceState(android.os.Bundle)
        fun onApplicationSaveInstanceState(savedInstanceState: Bundle) { }

        // Called when the last activity is destroy
        fun onApplicationDestroy() { }
    }

    private var started = 0
    private var resumed = 0
    private var transitionPossible = false
    private var isChangingConfigurations = false
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (started == 0 && !isChangingConfigurations)
            listener?.onApplicationCreated(savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
        if (started == 0 && !isChangingConfigurations)
            listener?.onApplicationStarted()
        started++
    }

    override fun onActivityResumed(activity: Activity) {
        if (resumed == 0 && !transitionPossible && !isChangingConfigurations)
            listener?.onApplicationResumed()
        if (isChangingConfigurations) isChangingConfigurations = false
        transitionPossible = false
        resumed++
    }

    override fun onActivityPaused(activity: Activity) {
        transitionPossible = true
        resumed--
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity.isChangingConfigurations) {
            isChangingConfigurations = true
        }
        if (started == 1 && !activity.isChangingConfigurations) {
            // We only know the application was paused when
            // it's stopped (because transitions always pause activities)
            // http://developer.android.com/guide/components/activities.html#CoordinatingActivities
            if (transitionPossible && resumed == 0) listener?.onApplicationPaused()
            listener?.onApplicationStopped()
        }
        transitionPossible = false
        started--
    }

    override fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle) {

        // https://developer.android.com/reference/android/app/Activity#onSaveInstanceState(android.os.Bundle)
        // If called, this method will occur after onStop() for applications targeting
        // platforms starting with Build.VERSION_CODES.P.
        // For applications targeting earlier platform versions this method will occur
        // before onStop() and there are no guarantees about whether it will occur
        // before or after onPause().
        val checkCounter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) 0 else 1

        if (started == checkCounter && !activity.isChangingConfigurations)
            listener?.onApplicationSaveInstanceState(savedInstanceState)
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (started == 0 && !activity.isChangingConfigurations)
            listener?.onApplicationDestroy()
    }
}
