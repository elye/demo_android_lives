package com.elyeproj.demoandroidlive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SAVE_KEY = "bundle"
    }

    var myVariable = false

    var myVariablePersist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            myVariablePersist = savedInstanceState.getBoolean(SAVE_KEY)
        }

        setImage()

        button_update.setOnClickListener {
            MainApplication.myVariable = true
            myVariable = true
            myVariablePersist = true
            setImage()
        }
    }

    private fun setImage() {
        image_activity.setImageResource(
                if (myVariable)
                    R.drawable.icon_live
                else
                    R.drawable.icon_dead)
        image_application.setImageResource(
                if (MainApplication.myVariable)
                    R.drawable.icon_live
                else
                    R.drawable.icon_dead)
        image_bundle.setImageResource(
                if (myVariablePersist)
                    R.drawable.icon_live
                else
                    R.drawable.icon_dead)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVE_KEY, myVariablePersist)
    }
}
