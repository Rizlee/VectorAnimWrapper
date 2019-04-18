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

    private var isFirstAnimation = true

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
        val bufState = if (isFirstAnimation) {
            isFirstAnimation = false
            currentState
        } else transitions[currentState]

        bufState?.let {
            when (it) {
                is AnimatedVectorDrawable -> it.start()
                is AnimatedVectorDrawableCompat -> it.start()
                else -> throw Exception("IncompatibilityException: first or last stateDrawable not AnimatedVectorDrawable/AnimatedVectorDrawableCompat")
            }
            currentState = it
            this.background = currentState
        }

        listener?.onClickEvent(if (currentState == firstStateIcon) firstStateId else lastStateId)
    }

    private fun setStateIds(firstStateId: Int, lastStateId: Int) {
        this.firstStateId = firstStateId
        this.lastStateId = lastStateId
    }

    fun init(firstStateId: Int, lastStateId: Int, listener: OnAnimatedIconClickListener) {
        setStateIds(firstStateId, lastStateId)
        this.listener = listener
    }

    interface OnAnimatedIconClickListener {
        fun onClickEvent(newState: Int)
    }
}