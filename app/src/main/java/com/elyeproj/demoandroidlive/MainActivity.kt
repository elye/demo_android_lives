package com.elyeproj.demoandroidlive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elyeproj.demoandroidlive.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val SAVE_KEY = "bundle"
    }

    private var myVariable = false
    private var myVariablePersist = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState != null) {
            myVariablePersist = savedInstanceState.getBoolean(SAVE_KEY)
        }

        setImage()

        binding.buttonUpdate.setOnClickListener {
            MainApplication.myVariable = true
            myVariable = true
            myVariablePersist = true
            setImage()
        }
    }

    private fun setImage() {
        with(binding) {
            imageActivity.setImageResource(
                if (myVariable)
                    R.drawable.icon_live
                else
                    R.drawable.icon_dead
            )
            imageApplication.setImageResource(
                if (MainApplication.myVariable)
                    R.drawable.icon_live
                else
                    R.drawable.icon_dead
            )
            imageBundle.setImageResource(
                if (myVariablePersist)
                    R.drawable.icon_live
                else
                    R.drawable.icon_dead
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVE_KEY, myVariablePersist)
    }
}
