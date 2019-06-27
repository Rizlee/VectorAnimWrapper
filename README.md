[![Platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-VectorAnimWrapper-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7658)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Download](https://api.bintray.com/packages/rizlee/vector-anim-wrapper/vector-anim-wrapper/images/download.svg?version=1.0.5)](https://bintray.com/rizlee/vector-anim-wrapper/vector-anim-wrapper/1.0.5/link)

# Usage
#### Gradle
```xml
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
just implement:
AnimatedIcon.OnAnimatedIconClickListener
AnimatedIcon.OnAnimatedIconCallback // only for api >= 23
 
then animatedIcon.listener = this
animatedIcon.animCallback = this // only for api >= 23
        
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
    
animatedIcon.setCurrentStateWithAnim(AnimatedIcon.State.FIRST_STATE)
animatedIcon.currentState // get the current state
```

#### Animated drawables you can make with the help of [this](https://shapeshifter.design/), In sample you can find examples of vector drawables
