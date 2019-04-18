[![Platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Download](https://api.bintray.com/packages/rizlee/vector-anim-wrapper/vector-anim-wrapper/images/download.svg?version=1.0.1)](https://bintray.com/rizlee/vector-anim-wrapper/vector-anim-wrapper/1.0.1/link)

# Usage
#### Gradle
```xml
maven{ url'https://dl.bintray.com/rizlee/vector-anim-wrapper' }

implementation "com.rizlee.wrapper:vector-anim-wrapper:$last_version"
```

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
            override fun onClickEvent(newStateId: Int) {
                when (newStateId) {
                    PLAY -> Log.i(TAG, "Play state")
                    PAUSE -> Log.i(TAG, "Pause state")
                }
            }

            override fun onStateChanged(newStateId: Int) {
                when (newStateId) {
                    PLAY -> Log.i(TAG, "Play state")
                    PAUSE -> Log.i(TAG, "Pause state")
                }
            }
        })
        
animatedIcon.setCurrentStateWithAnim(PLAY)
```

#### Animated drawables you can make with the help of [this](https://shapeshifter.design/)
