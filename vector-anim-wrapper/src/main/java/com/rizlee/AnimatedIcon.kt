package com.rizlee

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.rizlee.animatedicon.R

private const val DEFAULT_FIRST_STATE_ID = 0
private const val DEFAULT_LAST_STATE_ID = 1

class AnimatedIcon @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var firstStateIcon: Drawable
    private lateinit var lastStateIcon: Drawable

    private lateinit var currentState: Drawable

    private var firstStateId = DEFAULT_FIRST_STATE_ID
    private var lastStateId = DEFAULT_LAST_STATE_ID

    private lateinit var transitions: Map<Drawable, Drawable>

    private var listener: OnAnimatedIconClickListener? = null

    private var isReAnimNeed = true

    init {
        this.setOnClickListener { onClickEvent() }

        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.AnimatedIcon).apply {
                getDrawable(R.styleable.AnimatedIcon_first_state)?.let { drawable ->
                    firstStateIcon = drawable
                    currentState = firstStateIcon
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
        performAnim(if (isReAnimNeed) currentState else transitions[currentState])
        listener?.onClickEvent(if (currentState == firstStateIcon) firstStateId else lastStateId)
    }

    private fun setStateIds(firstStateId: Int, lastStateId: Int) {
        this.firstStateId = firstStateId
        this.lastStateId = lastStateId
    }

    private fun performAnim(newState: Drawable?) {
        newState?.let {
            when (it) {
                is AnimatedVectorDrawable -> it.start()
                is AnimatedVectorDrawableCompat -> it.start()
                else -> throw Exception("IncompatibilityException: first or last stateDrawable not AnimatedVectorDrawable/AnimatedVectorDrawableCompat")
            }
            isReAnimNeed = false
            newStateEvent(it)
        }
    }

    private fun newStateEvent(newState: Drawable) {
        currentState = newState
        this.background = currentState
        listener?.onStateChanged(if (currentState == firstStateIcon) firstStateId else lastStateId)
    }

    fun init(firstStateId: Int, lastStateId: Int, listener: OnAnimatedIconClickListener) {
        setStateIds(firstStateId, lastStateId)
        this.listener = listener
    }

    fun setCurrentStateWithAnim(nextStateId: Int) =
            (if (nextStateId == firstStateId) firstStateIcon else lastStateIcon).apply {
                if (this != currentState) {
                    performAnim(this)
                } else if (isReAnimNeed) performAnim(this)
            }

    //todo need to fix problem when setCurrentStateWithAnim -> setCurrentStateWithoutAnim (without onClick)
    /*fun setCurrentStateWithoutAnim(nextStateId: Int) =
            (if (nextStateId == firstStateId) firstStateIcon else lastStateIcon ).apply {
                if (this != currentState) {
                    newStateEvent(this)
                    isReAnimNeed = true
                }
            }*/

    interface OnAnimatedIconClickListener {
        fun onClickEvent(newStateId: Int)

        fun onStateChanged(newStateId: Int)
    }
}