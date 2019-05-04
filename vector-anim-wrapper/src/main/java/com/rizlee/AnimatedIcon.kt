package com.rizlee

import android.content.Context
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.rizlee.animatedicon.R

class AnimatedIcon @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var firstStateIcon: Drawable
    private lateinit var lastStateIcon: Drawable

    private lateinit var currentStateDrawable: Drawable
    val currentState: State
        get() = if (currentStateDrawable == firstStateIcon) State.FIRST_STATE else State.LAST_STATE

    private lateinit var transitions: Map<Drawable, Drawable>

    private var isReAnimNeed = true

    var listenerClick: OnAnimatedClickIconListener? = null

    @RequiresApi(Build.VERSION_CODES.M)
    var animCallback: OnAnimatedIconCallback? = null
        set(value) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) throw Exception("Require api >= 23")
            else field = value
        }

    init {
        this.setOnClickListener { onClickEvent() }

        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.AnimatedIcon).apply {
                getDrawable(R.styleable.AnimatedIcon_first_state)?.let { drawable ->
                    firstStateIcon = drawable
                    currentStateDrawable = firstStateIcon
                } ?: throw Exception("NoStateFoundException: firstState")

                getDrawable(R.styleable.AnimatedIcon_last_state)?.let { drawable ->
                    lastStateIcon = drawable
                } ?: throw Exception("NoStateFoundException: secondState")

                transitions = mapOf(
                        firstStateIcon to lastStateIcon,
                        lastStateIcon to firstStateIcon
                )

                recycle()
            }
            this.background = firstStateIcon
        }
    }

    private fun onClickEvent() {
        performAnim(if (isReAnimNeed) currentStateDrawable else transitions[currentStateDrawable])
        listenerClick?.onClickEvent(if (currentStateDrawable == firstStateIcon) State.FIRST_STATE else State.LAST_STATE)
    }

    private fun performAnim(newState: Drawable?) {
        newState?.let {
            when (it) {
                is AnimatedVectorDrawable -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        animCallback?.let { callback ->
                            it.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                                override fun onAnimationStart(drawable: Drawable?) {
                                    super.onAnimationStart(drawable)
                                    callback.onAnimationStart()
                                }

                                override fun onAnimationEnd(drawable: Drawable?) {
                                    super.onAnimationEnd(drawable)
                                    callback.onAnimationEnd()
                                    it.unregisterAnimationCallback(this)
                                }
                            })
                        }
                    }
                    it.start()
                }
                is AnimatedVectorDrawableCompat -> it.start()
                else -> throw Exception("IncompatibilityException: first or last stateDrawable not AnimatedVectorDrawable/AnimatedVectorDrawableCompat")
            }
            isReAnimNeed = false
            newStateEvent(it)
        }
    }

    private fun newStateEvent(newState: Drawable) {
        currentStateDrawable = newState
        this.background = currentStateDrawable
        listenerClick?.onStateChanged(if (currentStateDrawable == firstStateIcon) State.FIRST_STATE else State.LAST_STATE)
    }

    fun setCurrentStateWithAnim(nextState: State) =
            (if (nextState.stateId == State.FIRST_STATE.stateId) firstStateIcon else lastStateIcon).apply {
                if (this != currentStateDrawable) {
                    performAnim(this)
                } else if (isReAnimNeed) performAnim(this)
            }

    interface OnAnimatedClickIconListener {
        fun onClickEvent(newState: State)

        fun onStateChanged(newState: State)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    interface OnAnimatedIconCallback {
        fun onAnimationStart()

        fun onAnimationEnd()
    }

    enum class State(val stateId: Int) {
        FIRST_STATE(0),
        LAST_STATE(1)
    }
}