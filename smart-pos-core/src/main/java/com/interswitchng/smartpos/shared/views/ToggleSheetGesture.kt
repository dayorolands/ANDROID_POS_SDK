package com.interswitchng.smartpos.shared.views

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.utils.DisplayUtils.doOnEnd
import com.interswitchng.smartpos.shared.utils.DisplayUtils.getValueAnimator

internal class ToggleSheetGesture private constructor(
    private val mViewToBeAnimated: View,
    private val maxY: Int,
    private val callback: GestureSheetCallback
): GestureDetector.SimpleOnGestureListener(), View.OnTouchListener {

    interface GestureSheetCallback {
        fun onDismiss()
        fun onMove(percent: Float)
    }

    private enum class State { Dismissing, Reverting }

    var isDraggable = true
    private var mXDiffInTouchPointAndViewTopLeftCorner: Float = 0.toFloat()
    private var mYDiffInTouchPointAndViewTopLeftCorner: Float = 0.toFloat()
    private lateinit var mDetector: GestureDetector
    private var animator: Animator? = null
    private val minY: Int = 0
    private var state = State.Reverting


    init {
        // move view to top of screen
        completeAnimation(DecelerateInterpolator(), 400)
    }

    fun setDetector(detector: GestureDetector) {
        mDetector = detector
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (isDraggable) {

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // stop running animation
                    animator?.cancel()
                    // calculate the difference between touch point(event.getRawX()) on view & view's top left corner(v.getX())
                    mXDiffInTouchPointAndViewTopLeftCorner = event.rawX - v.x
                    mYDiffInTouchPointAndViewTopLeftCorner = event.rawY - v.y
                    mDetector.onTouchEvent(event)
                }

                MotionEvent.ACTION_MOVE -> {
                    val newTopLeftY = event.rawY - mYDiffInTouchPointAndViewTopLeftCorner

                    if (newTopLeftY > minY && newTopLeftY < maxY) {
                        mViewToBeAnimated.y = newTopLeftY

                        // trigger move callback
                        val percentMoved = (mViewToBeAnimated.y / maxY) * 100
                        callback.onMove(percentMoved)
                    }

                    mDetector.onTouchEvent(event)
                }

                MotionEvent.ACTION_UP -> {
                    if (!mDetector.onTouchEvent(event)) {
                        state = State.Reverting
                        completeAnimation()
                    }
                }

                else -> mDetector.onTouchEvent(event)
            }
        }
        return isDraggable
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onFling(downEvent: MotionEvent, moveEvent: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        val currentTop = mViewToBeAnimated.y
        // downEvent : when user puts his finger down on the view
        // moveEvent : when user lifts his finger at the end of the movement
        val distanceInY = moveEvent.rawY - downEvent.rawY - currentTop


        if (Math.abs(distanceInY) > MIN_DISTANCE_MOVED) {
            val movingUp = true
            val duration = IswConstants.ANIMATION_DURATION
            val interpolator = AccelerateDecelerateInterpolator()

            animator = getValueAnimator(movingUp, duration, interpolator) { progress ->
                val newTopLeftY = Math.max(0f, currentTop + (distanceInY * progress))
                mViewToBeAnimated.y = newTopLeftY

                // trigger move callback
                val percentMoved = (mViewToBeAnimated.y / maxY) * 100
                callback.onMove(percentMoved)
            }

            animator?.doOnEnd {
                val shouldDismiss = velocityY > 1000 && mViewToBeAnimated.y > maxY / 2
                state =
                    if (shouldDismiss) State.Dismissing
                    else State.Reverting

                completeAnimation()
            }

            animator?.start()
        } else {
            // cancel running animation
            animator?.cancel()

            // set state based on changed distance
            val shouldDismiss = velocityY > 1000 || mViewToBeAnimated.y > maxY / 2
            state =
                if (shouldDismiss) State.Dismissing
                else State.Reverting

            completeAnimation()
        }

        return true
    }

    private fun completeAnimation(interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(), duration: Long = IswConstants.ANIMATION_DURATION) {
        val movingUp = true

        val currentTop = mViewToBeAnimated.y
        val positionDiffernce = when (state) {
            State.Reverting -> minY - currentTop
            State.Dismissing -> maxY - currentTop
        }


        animator = getValueAnimator(movingUp, duration, interpolator) { progress ->
            mViewToBeAnimated.y = currentTop + (positionDiffernce * progress)

            // trigger move callback
            val percentMoved = (mViewToBeAnimated.y / maxY) * 100
            callback.onMove(percentMoved)
        }

        animator?.doOnEnd {
            if (state == State.Dismissing) {
                callback.onDismiss()
            }
        }

        animator?.start()

    }

    fun dismiss() {
        state = State.Dismissing
        completeAnimation(AccelerateInterpolator(), IswConstants.ANIMATION_DURATION)
    }

    companion object {
        private const val MIN_DISTANCE_MOVED = 20

        fun create(context: Context, view: View, viewPortHeight: Int, callback: GestureSheetCallback): ToggleSheetGesture {
            val handler = ToggleSheetGesture(view, viewPortHeight, callback)
            val gestureDetector = GestureDetector(context, handler)
            handler.setDetector(gestureDetector)

            return handler
        }
    }
}