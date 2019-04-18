package com.rizlee.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rizlee.AnimatedIcon
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

private const val PLAY = 10
private const val PAUSE = 11

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animatedIcon.init(PLAY, PAUSE, object : AnimatedIcon.OnAnimatedIconClickListener {
            override fun onClickEvent(newState: Int) {
                when (newState) {
                    PLAY -> Log.i(TAG, "Play state")
                    PAUSE -> Log.i(TAG, "Pause state")
                }
            }
        })
    }
}
