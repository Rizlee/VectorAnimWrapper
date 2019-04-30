package com.rizlee

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.rizlee.animatedicon.R

class AnimatedIcon @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var firstStateIcon: Drawable
    private lateinit var lastStateIcon: Drawable

    private lateinit var currentState: Drawable

    private lateinit var transitions: Map<Drawable, Drawable>

    private var isReAnimNeed = true

    var listener: OnAnimatedIconClickListener? = null

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
        listener?.onClickEvent(if (currentState == firstStateIcon) State.FIRST_STATE.stateId else State.LAST_STATE.stateId)
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
        listener?.onStateChanged(if (currentState == firstStateIcon) State.FIRST_STATE.stateId else State.LAST_STATE.stateId)
    }

    fun setCurrentStateWithAnim(nextStateId: Int) =
            (if (nextStateId == State.FIRST_STATE.stateId) firstStateIcon else lastStateIcon).apply {
                if (this != currentState) {
                    performAnim(this)
                } else if (isReAnimNeed) performAnim(this)
            }

    fun getCurrentState() = if (currentState == firstStateIcon) State.FIRST_STATE else State.LAST_STATE

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

    enum class State(val stateId: Int){
        FIRST_STATE(0),
        LAST_STATE(1)
    }
}