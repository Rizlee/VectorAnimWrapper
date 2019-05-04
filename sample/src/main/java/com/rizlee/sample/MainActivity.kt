package com.rizlee.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rizlee.AnimatedIcon
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), AnimatedIcon.OnAnimatedIconClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animatedIcon.listener = this

    }

    override fun onClickEvent(newState: AnimatedIcon.State) {
        when (newState) {
            AnimatedIcon.State.FIRST_STATE -> Log.i(TAG, "First state")
            AnimatedIcon.State.LAST_STATE -> Log.i(TAG, "Last state")
        }
    }

    override fun onStateChanged(newState: AnimatedIcon.State) {
        when (newState) {
            AnimatedIcon.State.FIRST_STATE -> Log.i(TAG, "First state")
            AnimatedIcon.State.LAST_STATE -> Log.i(TAG, "Last state")
        }
    }
}
