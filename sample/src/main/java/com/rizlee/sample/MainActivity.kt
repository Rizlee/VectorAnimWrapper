package com.rizlee.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rizlee.AnimatedIcon
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), AnimatedIcon.OnAnimatedClickIconListener, AnimatedIcon.OnAnimatedIconCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animatedIcon.listenerClick = this
        animatedIcon.animCallback = this
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

    override fun onAnimationStart() {
        Log.i(TAG, "onAnimationStart")
    }

    override fun onAnimationEnd() {
        Log.i(TAG, "onAnimationEnd")
    }
}
