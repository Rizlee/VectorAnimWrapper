[![Platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

# Usage
#### XML
```xml
<com.rizlee.animatedicon.AnimatedIcon
        android:id="@+id/animatedIcon"
        android:layout_width="50dp"
        android:layout_height="50dp"
        app:first_state="@drawable/play_to_pause"
        app:last_state="@drawable/pause_to_play" />
```

#### Kotlin
```kotlin
private const val PLAY = 10
private const val PAUSE = 11

animatedIcon.init(PLAY, PAUSE, object : AnimatedIcon.OnAnimatedIconClickListener {
            override fun onClickEvent(newState: Int) {
                when (newState) {
                    PLAY -> Log.i(TAG, "Play state")
                    PAUSE -> Log.i(TAG, "Pause state")
                }
            }
        })
```

#### Animated drawables you can make with the help of [this](https://shapeshifter.design/)
